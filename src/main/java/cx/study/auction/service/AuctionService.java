package cx.study.auction.service;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.AuctionType;
import cx.study.auction.bean.Customer;
import cx.study.auction.bean.ImageUrl;
import cx.study.auction.constants.Constants.AuctionStatus;
import cx.study.auction.dao.AuctionRepository;
import cx.study.auction.dao.AuctionTypeRepository;
import cx.study.auction.dao.CustomerRepository;
import cx.study.auction.dao.ImageUrlRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by cheng.xiao on 2017/5/4.
 */
@Service
public class AuctionService {

    @Resource
    private AuctionRepository auctionRepository;
    @Resource
    private AuctionTypeRepository auctionTypeRepository;
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private ImageUrlRepository imageUrlRepository;
    public Page<Auction> findAll(int start, int length, String query){
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return auctionRepository.findAll(new Specification<Auction>() {
            @Override
            public Predicate toPredicate(Root<Auction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(query)){
                    Path<String> name = root.get("name");
                    Path<String> typeName = root.get("type").get("typeName");
                    Predicate predicate1 = criteriaBuilder.like(name, "%" + query + "%");
                    Predicate predicate2 = criteriaBuilder.like(typeName, "%" + query + "%");
                    criteriaQuery.where(criteriaBuilder.or(predicate1,predicate2));
                }
                return null;
            }
        },pageRequest);
    }

    public Auction findById(Integer id){
        return auctionRepository.findOne(id);
    }
    @Transactional
    public Auction add(Auction auction,String urls){
        AuctionType type = auctionTypeRepository.findOne(auction.getTypeId());
        Customer customer = customerRepository.findOne(auction.getCustomerId());
        auction.setCreateTime(new Date().getTime());
        auction.setUpdateTime(new Date().getTime());
        auction.setType(type);
        auction.setCustomer(customer);
        Auction save = auctionRepository.save(auction);
        saveImageUrl(urls, save);
        return save;
    }
    @Transactional
    public Auction update(Auction auction,String urls){
        AuctionType type = auctionTypeRepository.findOne(auction.getTypeId());
        Customer customer = customerRepository.findOne(auction.getCustomerId());
        Auction one = auctionRepository.findOne(auction.getId());
        auction.setCreateTime(one.getCreateTime());
        auction.setStartTime(one.getStartTime());
        auction.setEndTime(one.getEndTime());
        auction.setUpdateTime(new Date().getTime());
        auction.setType(type);
        auction.setCustomer(customer);
        Auction save = auctionRepository.save(auction);
        saveImageUrl(urls, save);
        return save;
    }

    private void saveImageUrl(String urls, Auction save) {
        if (!StringUtils.isEmpty(urls)){
            urls = urls.substring(1,urls.length());
            String[] split = urls.split(",");
            for (String url : split){
                ImageUrl imageUrl = new ImageUrl();
                imageUrl.setUrl(url);
                imageUrl.setAuction(save);
                imageUrlRepository.save(imageUrl);
            }
        }
    }

    @Transactional
    public void delete(String id){
        auctionRepository.delete(Integer.parseInt(id));
    }

    @Transactional
    public Auction setting(Integer status, Integer id,Date startTime,Date endTime){
        Auction one = auctionRepository.findOne(id);
        one.setStatus(status);

        if (status.equals(AuctionStatus.WAIT_AUCTION) || status.equals(AuctionStatus.AUCTION)){
            //只有 待拍卖 和 正在拍卖需要设置时间
            one.setStartTime(startTime.getTime());
            one.setEndTime(endTime.getTime());
            doAuction(one);
        }
        one.setUpdateTime(new Date().getTime());
        return auctionRepository.save(one);
    }
    public void startTimer(){
        List<Auction> auctions = auctionRepository.findByStatus(AuctionStatus.WAIT_AUCTION);
        auctions.addAll(auctionRepository.findByStatus(AuctionStatus.AUCTION));
        for (Auction auction : auctions){
            doAuction(auction);
        }
    }
    @Transactional
    public void doAuction(final Auction auction){
        long startTime = auction.getStartTime();
        long endTime = auction.getEndTime();
        if (System.currentTimeMillis() < startTime){
            //还未开始
            auction.setStatus(AuctionStatus.WAIT_AUCTION);
            auction.setUpdateTime(new Date().getTime());
            Auction save = auctionRepository.save(auction);
            start(save,startTime);
            end(save,endTime);
        } else if (startTime <= System.currentTimeMillis() && System.currentTimeMillis() < endTime){
            //正在进行
            auction.setStatus(AuctionStatus.AUCTION);
            auction.setUpdateTime(new Date().getTime());
            Auction save = auctionRepository.save(auction);
            end(save,endTime);
        } else {
            //已经结束
            doEnd(auction);
        }
    }

    private void start(Auction auction,long startTime){
        long delay = startTime - System.currentTimeMillis();
        startScheduledExecutorService(delay, new Runnable() {
            @Override
            public void run() {
                doStart(auction);
            }
        });

    }
    private void end(Auction auction ,long endTime){
        long delay = endTime - System.currentTimeMillis();
        startScheduledExecutorService(delay, new Runnable() {
            @Override
            public void run() {
                doEnd(auction);
            }
        });
    }

    private void doStart(Auction auction){
        auction.setStatus(AuctionStatus.AUCTION);
        auction.setUpdateTime(new Date().getTime());
        auctionRepository.save(auction);
    }

    private void doEnd(Auction auction){
        Auction one = auctionRepository.findOne(auction.getId());
        double hammerPrice = one.getHammerPrice();
        if (hammerPrice > 0d){
            //成交
            one.setStatus(AuctionStatus.SUCCESS);
            //生成订单
            //返还保证金
        } else {
            //流拍
            one.setStatus(AuctionStatus.UNSOLD);
        }
        one.setUpdateTime(new Date().getTime());
        auctionRepository.save(one);
    }
    private void startScheduledExecutorService(long delay,Runnable runnable){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(runnable,delay, TimeUnit.MILLISECONDS);
        System.out.println("开始定时任务：" + new Date(System.currentTimeMillis() + delay));
    }

}
