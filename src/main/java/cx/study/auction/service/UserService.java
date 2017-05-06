package cx.study.auction.service;

import cx.study.auction.bean.User;
import cx.study.auction.dao.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User login(String username,String password){
        return userRepository.findByUsernameAndPassword(username,password);
    }
    @Transactional
    public User register(User user){
        user.setCreateTime(new Date().getTime());
        user.setUpdateTime(new Date().getTime());
        return userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User finOne(Integer id){
        return userRepository.findOne(id);
    }
    @Transactional
    public User update(User user){
        user.setUpdateTime(new Date().getTime());
        return userRepository.save(user);
    }
}
