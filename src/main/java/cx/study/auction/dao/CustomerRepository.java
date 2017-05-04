package cx.study.auction.dao;

import cx.study.auction.bean.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * Created by cheng.xiao on 2017/5/4.
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer> ,JpaSpecificationExecutor<Customer>
        ,PagingAndSortingRepository<Customer,Integer> {
}
