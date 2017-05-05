package cx.study.auction.service;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.AuctionType;
import cx.study.auction.bean.Customer;
import cx.study.auction.bean.ImageUrl;
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
import javax.persistence.Transient;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


}
