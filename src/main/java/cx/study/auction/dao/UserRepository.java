package cx.study.auction.dao;

import cx.study.auction.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
public interface UserRepository extends JpaRepository<User,Integer>{

    User findByUsernameAndPassword(String username,String password);

    User findByUsername(String username);
}
