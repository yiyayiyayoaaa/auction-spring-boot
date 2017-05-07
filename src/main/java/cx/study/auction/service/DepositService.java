package cx.study.auction.service;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.Deposit;
import cx.study.auction.bean.User;
import cx.study.auction.dao.AuctionRepository;
import cx.study.auction.dao.DepositRepository;
import cx.study.auction.dao.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/7.
 */
@Service
public class DepositService {

    @Resource
    private DepositRepository depositRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private AuctionRepository auctionRepository;

    @Transactional
    public int add(Integer userId,Integer auctionId){
        Deposit deposit = new Deposit();
        User user = userRepository.findOne(userId);
        Auction auction = auctionRepository.findOne(auctionId);
        double account = user.getAccount();
        double deposit1 = auction.getDeposit();
        if (deposit1 > account){
            return -1; //余额不足
        }
        user.setAccount(account-deposit1);
        user.setUpdateTime(new Date().getTime());
        userRepository.save(user);
        deposit.setTime(new Date().getTime());
        deposit.setAuction(auction);
        deposit.setUser(user);
        return depositRepository.save(deposit) == null ? 1 : 0;
    }

    public Deposit find(Integer userId,Integer auctionId){
        return depositRepository.findOne(new Specification<Deposit>() {
            @Override
            public Predicate toPredicate(Root<Deposit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> aid = root.get("auction").get("id");
                Path<Integer> uid = root.get("user").get("id");
                Predicate auction = criteriaBuilder.equal(aid, auctionId);
                Predicate user = criteriaBuilder.equal(uid, userId);
                criteriaQuery.where(criteriaBuilder.and(auction,user));
                return null;
            }
        });
    }

    public List<Auction>  findByUserId(Integer userId){
        List<Deposit> deposits = depositRepository.findAll(new Specification<Deposit>() {
            @Override
            public Predicate toPredicate(Root<Deposit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> uid = root.get("user").get("id");
                criteriaQuery.where(criteriaBuilder.equal(uid, userId));
                return null;
            }
        });
        List<Auction> auctions = new ArrayList<>();
        for (Deposit deposit : deposits){
            auctions.add(deposit.getAuction());
        }
        return auctions;
    }
}
