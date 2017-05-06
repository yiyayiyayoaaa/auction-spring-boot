package cx.study.auction.service;

import cx.study.auction.bean.UserAddress;
import cx.study.auction.dao.UserAddressRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.beans.Transient;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@Service
public class UserAddressService {

    @Resource
    private UserAddressRepository userAddressRepository;
    @Transactional
    public UserAddress add(UserAddress userAddress){
        return userAddressRepository.save(userAddress);
    }
    @Transactional
    public UserAddress update(UserAddress userAddress){
        return userAddressRepository.save(userAddress);
    }
    @Transactional
    public void delete(Integer id){
        userAddressRepository.delete(id);
    }

    public UserAddress findOne(Integer id){
        return userAddressRepository.findOne(id);
    }
}
