package cx.study.auction.service;

import cx.study.auction.bean.BidRecord;
import cx.study.auction.dao.BidRecordRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@Service
public class BidRecordService {

    @Resource
    private BidRecordRepository bidRecordRepository;

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

    public BidRecord add(BidRecord bidRecord){
        return bidRecordRepository.save(bidRecord);
    }
}
