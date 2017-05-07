package cx.study.auction.dao;

import cx.study.auction.bean.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * Created by chengxiao on 2017/5/7.
 */
public interface OrderRepository extends JpaRepository<OrderInfo,Integer>,JpaSpecificationExecutor<OrderInfo> {
}
