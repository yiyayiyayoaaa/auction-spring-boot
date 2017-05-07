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
}
