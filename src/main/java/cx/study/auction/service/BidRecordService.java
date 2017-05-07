package cx.study.auction.service;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.BidRecord;
import cx.study.auction.bean.User;
import cx.study.auction.dao.BidRecordRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@Service
public class BidRecordService {

    @Resource
    private BidRecordRepository bidRecordRepository;
    @Resource
    private AuctionService auctionService;
    @Resource
    private UserService userService;
    public List<BidRecord> find(int auctionId){
        Sort sort = new Sort(Sort.Direction.DESC,"bidTime");
        return bidRecordRepository.findAll(new Specification<BidRecord>() {
            @Override
            public Predicate toPredicate(Root<BidRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> path = root.get("auction").get("id");
                criteriaQuery.where(criteriaBuilder.equal(path,auctionId));
                return null;
            }
        },sort);
    }
    @Transactional
    public BidRecord bid(Integer auctionId,Integer userId,double price){
        Auction auction = auctionService.findById(auctionId);
        if (auction.getHammerPrice() >= price){
            return null;
        }
        auction.setHammerPrice(price);
        auctionService.update(auction);
        User user = userService.finOne(userId);
        BidRecord bidRecord = new BidRecord();
        bidRecord.setAuction(auction);
        bidRecord.setPrice(price);
        bidRecord.setUser(user);
        bidRecord.setBidTime(System.currentTimeMillis());
        return add(bidRecord);
    }
    @Transactional
    public BidRecord add(BidRecord bidRecord){
        return bidRecordRepository.save(bidRecord);
    }
}
