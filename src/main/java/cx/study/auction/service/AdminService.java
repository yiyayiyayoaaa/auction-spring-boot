package cx.study.auction.service;

import cx.study.auction.bean.Admin;
import cx.study.auction.bean.Auction;
import cx.study.auction.dao.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

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

    public Admin update(Admin admin){
        return adminRepository.save(admin);
    }

    public Page<Admin> findAll(int start, int length, String query){
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return adminRepository.findAll(new Specification<Admin>() {
            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(query)){
                    Path<String> username = root.get("username");
                    Predicate predicate = criteriaBuilder.like(username, "%" + query + "%");
                    criteriaQuery.where(predicate);
                }
                return null;
            }
        },pageRequest);
    }

    @Transactional //修改密码
    public Admin updatePassword(Integer id,String password){
        Admin admin = adminRepository.findOne(id);
        admin.setPassword(password);
        admin.setUpdateTime(System.currentTimeMillis());
        return  adminRepository.save(admin);
    }

    @Transactional //重置密码
    public Admin resetPassword(Integer id){
        Admin admin = adminRepository.findOne(id);
        admin.setPassword("123456");
        admin.setUpdateTime(System.currentTimeMillis());
        return  adminRepository.save(admin);
    }

    @Transactional
    public Admin add(String username,int level){
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setLevel(level);
        admin.setCreateTime(System.currentTimeMillis());
        return adminRepository.save(admin);
    }

    @Transactional
    public void delete(String id){
        adminRepository.delete(Integer.parseInt(id));
    }

    @Transactional
    public void deleteBatch(String ids){
        ids = ids.substring(1,ids.length());
        String[] split = ids.split(",");
        for (String id : split){
            delete(id);
        }
    }
}
