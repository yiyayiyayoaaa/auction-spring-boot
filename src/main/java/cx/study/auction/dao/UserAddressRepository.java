package cx.study.auction.dao;

import cx.study.auction.bean.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
public interface UserAddressRepository extends JpaRepository<UserAddress,Integer>{
}
