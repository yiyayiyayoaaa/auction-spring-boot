package cx.study.auction.service;

import cx.study.auction.bean.User;
import cx.study.auction.constants.Constants;
import cx.study.auction.dao.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
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

    public Page<User> findAll(int start, int length, String query) {
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(query)){
                    Path<String> username = root.get("username");
                    Path<String> nickname = root.get("nickname");
                    Predicate predicate1 = criteriaBuilder.like(username, "%" + query + "%");
                    Predicate predicate2 = criteriaBuilder.like(nickname, "%" + query + "%");
                    criteriaQuery.where(criteriaBuilder.or(predicate1,predicate2));
                }
                return null;
            }
        },pageRequest);
    }

    @Transactional
    public User stop(Integer id){
        User one = userRepository.findOne(id);
        one.setStatus(Constants.UserStatus.DISABLE);
        one.setUpdateTime(System.currentTimeMillis());
        return userRepository.save(one);
    }

    @Transactional
    public User start(Integer id){
        User one = userRepository.findOne(id);
        one.setStatus(Constants.UserStatus.USER_ING);
        one.setUpdateTime(System.currentTimeMillis());
        return userRepository.save(one);
    }
}
