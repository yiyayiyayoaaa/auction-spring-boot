package cx.study.auction.dao;

import cx.study.auction.bean.BidRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
public interface BidRecordRepository extends JpaRepository<BidRecord,Integer>,JpaSpecificationExecutor<BidRecord>{
}
