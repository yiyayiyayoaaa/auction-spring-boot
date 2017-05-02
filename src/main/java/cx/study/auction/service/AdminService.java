package cx.study.auction.service;

import cx.study.auction.bean.Admin;
import cx.study.auction.dao.AdminRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by chengxiao on 2017/5/1.
 */
@Service
public class AdminService {

    @Resource
    private AdminRepository adminRepository;

    public Admin login(String username,String password){
        return adminRepository.findAdminByUsernameAndPassword(username,password);
    }

    public Admin AdminUpdate(Admin admin){
        return adminRepository.save(admin);
    }
}
