package cx.study.auction.rest;

import com.google.gson.JsonObject;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.bean.User;
import cx.study.auction.bean.UserAddress;
import cx.study.auction.service.UserAddressService;
import cx.study.auction.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@RestController
@RequestMapping("/UserAddressRest")
public class UserAddressRest {
    @Resource
    private UserService userService;
    @Resource
    private UserAddressService userAddressService;

    @RequestMapping("/getAll")
    public HttpResult<List<UserAddress>> addressList(@RequestBody() JsonObject json) throws Exception {
        int id = json.get("id").getAsInt();
        User user = userService.finOne(id);
        return new HttpResult<>(0,"",user.getAddresses());
    }

    @RequestMapping("/add")
    public HttpResult<UserAddress> add(@RequestBody()JsonObject json) throws Exception{
        int id = json.get("userId").getAsInt();
        User user = userService.finOne(id);
        String address = json.get("address").getAsString();
        String[] strings = address.split("@");
        String name = strings[0];
        String phone = strings[1];
        String addr = strings[2] + strings[3];
        UserAddress userAddress = new UserAddress();
        userAddress.setName(name);
        userAddress.setPhone(phone);
        userAddress.setAddress(addr);
        userAddress.setUser(user);
        UserAddress add = userAddressService.add(userAddress);
        if (add != null){
            return new HttpResult<>(0,"地址添加成功",add);
        } else {
            return new HttpResult<>(1,"地址添加失败",null);
        }
    }

    @RequestMapping("/update")
    public HttpResult<UserAddress> update(@RequestBody() JsonObject json) throws Exception{
        int id = json.get("id").getAsInt();
        int userId = json.get("userId").getAsInt();
        String address = json.get("address").getAsString();
        UserAddress userAddress = userAddressService.findOne(id);
        String[] strings = address.split("@");
        String name = strings[0];
        String phone = strings[1];
        String addr = strings[2] + "@" +strings[3];
        userAddress.setName(name);
        userAddress.setPhone(phone);
        userAddress.setAddress(addr);
        UserAddress update = userAddressService.update(userAddress);
        if (update != null){
            return new HttpResult<>(0,"地址修改成功",update);
        } else {
            return new HttpResult<>(1,"地址修改失败",null);
        }

    }
    @RequestMapping("/delete")
    public HttpResult<UserAddress> delete(@RequestBody() JsonObject json) throws Exception{
        int id = json.get("id").getAsInt();
        userAddressService.delete(id);
        return new HttpResult<>(0,"请求成功",null);
    }
}
