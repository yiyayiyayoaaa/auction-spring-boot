package cx.study.auction.service;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.AuctionType;
import cx.study.auction.bean.Customer;
import cx.study.auction.dao.AuctionRepository;
import cx.study.auction.dao.AuctionTypeRepository;
import cx.study.auction.dao.CustomerRepository;
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
import java.util.Date;

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
    public Page<Auction> findAll(int start, int length, String query){
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return auctionRepository.findAll(new Specification<Auction>() {
            @Override
            public Predicate toPredicate(Root<Auction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(query)){
                    Path<String> typeName = root.get("name");
                    criteriaQuery.where(criteriaBuilder.like(typeName,"%"+query+"%"));
                }
                return null;
            }
        },pageRequest);
    }
    @Transactional
    public Auction add(Auction auction){
        AuctionType type = auctionTypeRepository.findOne(auction.getTypeId());
        Customer customer = customerRepository.findOne(auction.getCustomerId());
        auction.setCreateTime(new Date().getTime());
        auction.setUpdateTime(new Date().getTime());
        auction.setType(type);
        auction.setCustomer(customer);
        return auctionRepository.save(auction);
    }
    @Transactional
    public Auction update(Auction auction){
        Auction one = auctionRepository.findOne(auction.getId());
        auction.setCreateTime(one.getCreateTime());
        auction.setUpdateTime(new Date().getTime());
        return auctionRepository.save(auction);
    }
}
