package cx.study.auction.dao;

import cx.study.auction.bean.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * Created by chengxiao on 2017/5/1.
 */
public interface AdminRepository extends JpaRepository<Admin,Integer>,PagingAndSortingRepository<Admin,Integer>,JpaSpecificationExecutor<Admin> {

    Admin findAdminByUsernameAndPassword(String username,String password);
}
