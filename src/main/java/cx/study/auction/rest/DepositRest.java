package cx.study.auction.rest;

import com.google.gson.JsonObject;
import cx.study.auction.bean.Deposit;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.service.DepositService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * Created by chengxiao on 2017/5/7.
 */
@RestController
@RequestMapping("/DepositRest")
public class DepositRest {
    @Resource
    private DepositService depositService;
    @RequestMapping("/pay")
    public HttpResult<String> pay(@RequestBody()JsonObject json) throws Exception{
        //支付保证金
        int userId = json.get("userId").getAsInt();
        int commodityId = json.get("commodityId").getAsInt();
        int add = depositService.add(userId, commodityId);
        String msg = null;
        switch (add){
            case -1:
                msg = "余额不足";
                break;
            case 0:
                msg = "支付成功";
                break;
            case 1:
                msg = "支付失败";
                break;
        }
        return new HttpResult<>(add,msg,null);
    }

    @RequestMapping("/isPayDeposit")
    public HttpResult<String> isPayDeposit( @RequestBody()JsonObject json) throws Exception{
        int userId = json.get("userId").getAsInt();
        int commodityId = json.get("commodityId").getAsInt();
        Deposit deposit = depositService.find(userId, commodityId);
        if (deposit != null){
            return new HttpResult<>(0,"请求成功",null);
        } else {
            return new HttpResult<>(1,"请求失败",null);
        }
    }
}
