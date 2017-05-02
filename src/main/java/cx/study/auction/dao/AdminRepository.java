package cx.study.auction.dao;

import cx.study.auction.bean.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * Created by chengxiao on 2017/5/1.
 */
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findAdminByUsernameAndPassword(String username,String password);
}
