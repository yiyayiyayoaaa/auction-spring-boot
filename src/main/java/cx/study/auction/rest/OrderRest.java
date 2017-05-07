package cx.study.auction.rest;

import com.google.gson.JsonObject;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.bean.OrderInfo;
import cx.study.auction.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/7.
 */
@RestController
@RequestMapping("OrderRest")
public class OrderRest {

    @Resource
    private OrderService orderService;

    @RequestMapping("/orderInfo")
    public HttpResult<OrderInfo> getOrderById(@RequestBody()JsonObject json) throws Exception {
        int id = json.get("id").getAsInt();
        OrderInfo order = orderService.findById(id);
        if (order != null){
            return new HttpResult<>(0,"请求成功",order);
        } else {
            return new HttpResult<>(1,"请求失败",null);
        }
    }


    @RequestMapping("/orderList")
    public HttpResult<List<OrderInfo>> getOrderListByStatus(@RequestBody()JsonObject json) throws Exception{
        int userId = json.get("userId").getAsInt();
        int status = json.get("status").getAsInt();
        List<OrderInfo> orderInfos = orderService.finAll(userId, status);
        return new HttpResult<>(0,"请求成功",orderInfos);
    }
    //支付   支付成功退回保证金
    @RequestMapping("/pay")
    public HttpResult<String> pay(@RequestBody()JsonObject json) throws Exception {
        int id = json.get("id").getAsInt();  //订单id
        String address = json.get("address").getAsString();
        //支付逻辑
        int pay = orderService.pay(id, address);
        String msg = null;
        switch (pay){
            case -1:
                msg = "余额不足!";
                break;
            case 0:
                msg = "支付成功!";
                break;
            case 1:
                msg = "支付失败!";
                break;
        }
        return new HttpResult<>(pay,msg,null);
    }
    //取消订单  不退保证金
    @RequestMapping("/cancel")
    public HttpResult<String> cancel(@RequestBody()JsonObject json) throws Exception {
        int id = json.get("id").getAsInt();
        OrderInfo orderInfo = orderService.cancel(id);
        if (orderInfo == null){
            return new HttpResult<>(0,"请求成功",null);
        } else {
            return new HttpResult<>(1,"请求失败",null);
        }
    }

    @RequestMapping("/finish")
    public HttpResult<String> finish(@RequestBody()JsonObject json) throws Exception {
        int id = json.get("id").getAsInt();
        OrderInfo finish = orderService.finish(id);
        if (finish == null){
            return new HttpResult<>(0,"请求成功",null);
        } else {
            return new HttpResult<>(1,"请求失败",null);
        }
    }
}
