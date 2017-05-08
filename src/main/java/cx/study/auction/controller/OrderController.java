package cx.study.auction.controller;

import cx.study.auction.bean.HttpResult;
import cx.study.auction.bean.OrderInfo;
import cx.study.auction.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chengxiao on 2017/5/8.
 */
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/order-list")
    public Map<String,Object> list(int start, int length, @RequestParam("search[value]") String query){
        Map<String,Object> map = new HashMap<>();
        Page<OrderInfo> page = orderService.findAll(start, length,query);
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return map;
    }

    @PostMapping("/order-send")
    public HttpResult<OrderInfo> orderSend(Integer id){
        OrderInfo send = orderService.send(id);
        return new HttpResult<>(0,"",send);
    }

    @PostMapping("/order-cancel")
    public HttpResult<OrderInfo> orderCancel(Integer id){
        OrderInfo cancel = orderService.cancel(id);
        return new HttpResult<>(0,"",cancel);
    }
}
