package cx.study.auction.controller;

import cx.study.auction.bean.AuctionType;
import cx.study.auction.bean.Customer;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by cheng.xiao on 2017/5/4.
 */
@RestController
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/customer-list")
    public Map<String,Object> list(int start,int length,@RequestParam("search[value]") String query){
        Map<String,Object> map = new HashMap<>();
        Page<Customer> page = customerService.findAll(start, length,query);
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return map;
    }

    @PostMapping("/add-customer")
    public HttpResult<String> add(Customer customer){
        Customer add = customerService.add(customer);
        if (add != null){
            return new HttpResult<>(0,"添加成功",null);
        }
        return new HttpResult<>(1,"添加失败",null);
    }

    @PostMapping("/update-customer")
    public HttpResult<String> update(Customer customer){
        Customer update = customerService.update(customer);
        if (update != null){
            return new HttpResult<>(0,"编辑成功",null);
        }
        return new HttpResult<>(1,"编辑失败",null);
    }

    @PostMapping("/delete-batch-customer")
    public HttpResult<String> deleteBatch(String ids){
        ids = ids.substring(1, ids.length());
        String[] array = ids.split(",");
        for (String id : array) {
            customerService.delete(id);
        }
        return new HttpResult<>(0,"删除成功",null);
    }

    @PostMapping("/delete-customer")
    public HttpResult<String> delete(String id){
        customerService.delete(id);
        return new HttpResult<>(0,"删除成功",null);
    }
}
