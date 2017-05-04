package cx.study.auction.service;

import cx.study.auction.bean.Customer;
import cx.study.auction.dao.CustomerRepository;
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
import java.util.List;

/**
 *
 * Created by cheng.xiao on 2017/5/4.
 */
@Service
public class CustomerService {
    @Resource
    private CustomerRepository customerRepository;
    public Page<Customer> findAll(int start, int length,String query){
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return customerRepository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(query)){
                    Path<String> name = root.get("name");
                    criteriaQuery.where(criteriaBuilder.like(name,"%"+query+"%"));
                }
                return null;
            }
        },pageRequest);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(Integer id){
        return customerRepository.findOne(id);
    }

    @Transactional
    public Customer add(Customer customer){
        customer.setCreateTime(new Date().getTime());
        customer.setUpdateTime(new Date().getTime());
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer update(Customer customer){
        Customer one = customerRepository.findOne(customer.getId());
        customer.setCreateTime(one.getCreateTime());
        customer.setUpdateTime(new Date().getTime());
        return customerRepository.save(customer);
    }

    @Transactional
    public void delete(String id){
        customerRepository.delete(Integer.parseInt(id));
    }
}
