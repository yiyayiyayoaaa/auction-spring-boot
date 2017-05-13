package cx.study.auction.service;

import cx.study.auction.bean.*;
import cx.study.auction.constants.Constants;
import cx.study.auction.constants.Constants.AuctionStatus;
import cx.study.auction.constants.Constants.OrderStatus;
import cx.study.auction.dao.AuctionRepository;
import cx.study.auction.dao.OrderRepository;
import cx.study.auction.dao.UserRepository;
import cx.study.auction.utils.OrderNumUtil;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/7.
 */
@Service
public class OrderService {
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private AuctionRepository auctionRepository;
    @Resource
    private BidRecordService bidRecordService;
    public OrderInfo findById(Integer id){
        return orderRepository.findOne(id);
    }

    public List<OrderInfo> finAll(Integer userId,int status){
        return orderRepository.findAll(new Specification<OrderInfo>() {
            @Override
            public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> uid = root.get("user").get("id");
                Path<Integer> s = root.get("status");
                Predicate pUid = criteriaBuilder.equal(uid, userId);
                Predicate pStatus = criteriaBuilder.equal(s, status);
                if (status < 0 ){
                    criteriaQuery.where(pUid);
                    return null;
                }
                criteriaQuery.where(criteriaBuilder.and(pUid,pStatus));
                return null;
            }
        });
    }
    @Transactional
    public int pay(Integer id,String address){
        OrderInfo order = orderRepository.findOne(id);
        Auction auction = order.getAuction();
        User user = order.getUser();
        double account = user.getAccount();
        double price = order.getPrice();
        if (account < price){
            //余额不足 返回 -1
            return -1;
        }
        user.setAccount(account - price);//退还保证金
        userRepository.save(user);
        order.setStatus(OrderStatus.WAIT_SEND);
        order.setPayTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        order.setAddress(address.replace("@"," "));
        return orderRepository.save(order) == null ? 1 : 0;
    }
    @Transactional
    public OrderInfo cancel(Integer id){
        OrderInfo order = orderRepository.findOne(id);
        if (order.getStatus() == OrderStatus.CANCEL ||
                order.getStatus() == OrderStatus.FINISH){
            return null;
        }
        order.setStatus(OrderStatus.CANCEL);
        order.setUpdateTime(System.currentTimeMillis());
        order.setEndTime(System.currentTimeMillis());
        Auction auction = order.getAuction();
        auction.setStatus(AuctionStatus.CANCEL);
        auction.setStartTime(null);
        auction.setEndTime(null);
        auction.setUpdateTime(System.currentTimeMillis());
        auctionRepository.save(auction);
        return orderRepository.save(order);
    }
    @Transactional
    public OrderInfo send(Integer id){
        OrderInfo one = orderRepository.findOne(id);
        one.setStatus(OrderStatus.WAIT_RECEIVED);
        one.setUpdateTime(System.currentTimeMillis());
        return orderRepository.save(one);
    }
    @Transactional
    public OrderInfo finish(Integer id){
        OrderInfo order = orderRepository.findOne(id);
        if (order.getStatus() == OrderStatus.CANCEL){
            return null;
        }
        order.setStatus(OrderStatus.FINISH);
        order.setUpdateTime(System.currentTimeMillis());
        order.setEndTime(System.currentTimeMillis());
        Auction auction = order.getAuction();
        User user = order.getUser();
        double deposit = auction.getDeposit();
        user.setAccount(user.getAccount() + deposit);
        user.setUpdateTime(System.currentTimeMillis());
        userRepository.save(user);
        return orderRepository.save(order);
    }

    public OrderInfo updateOrderStatus(Integer id,int status){
        OrderInfo order = orderRepository.findOne(id);
        order.setStatus(status);
        order.setUpdateTime(System.currentTimeMillis());
        return orderRepository.save(order);
    }
    @Transactional
    public void createOrder(Auction auction){
        if (auction.getStatus() == AuctionStatus.SUCCESS){
            OrderInfo order = new OrderInfo();
            order.setOrderNum(OrderNumUtil.createOrderNum());
            order.setStatus(OrderStatus.WAIT_PAY);
            List<BidRecord> records = bidRecordService.find(auction.getId());
            order.setAuction(auction);
            BidRecord record = records.get(0);
            order.setPrice(record.getPrice());
            order.setCreateTime(System.currentTimeMillis());
            order.setUpdateTime(System.currentTimeMillis());
            List<ImageUrl> imageUrls = auction.getImageUrls();
            if (imageUrls != null && imageUrls.size() > 0) {
                order.setUrl(imageUrls.get(0).getUrl());
            }
            order.setUser(record.getUser());
            orderRepository.save(order);
        }
    }

    public Page<OrderInfo> findAll(int start, int length, String query) {
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return orderRepository.findAll(new Specification<OrderInfo>() {
            @Override
            public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(query)){
                    Path<String> orderNum = root.get("orderNum");
                    Path<String> username = root.get("user").get("username");
                    Path<String> auctionName = root.get("auction").get("name");
                    Predicate predicate1 = criteriaBuilder.like(orderNum, "%" + query + "%");
                    Predicate predicate2 = criteriaBuilder.like(username, "%" + query + "%");
                    Predicate predicate3 = criteriaBuilder.like(auctionName, "%" + query + "%");
                    criteriaQuery.where(criteriaBuilder.or(predicate1,predicate2,predicate3));
                }
                return null;
            }
        },pageRequest);
    }

    public long countByStatus(int status){
        return orderRepository.count(new Specification<OrderInfo>() {
            @Override
            public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> s = root.get("status");
                criteriaQuery.where(criteriaBuilder.equal(s,status));
                return null;
            }
        });
    }

    public long count(){
        return orderRepository.count();
    }
}
