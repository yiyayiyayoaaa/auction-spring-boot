package cx.study.auction.rest;

import com.google.gson.JsonObject;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.bean.User;
import cx.study.auction.bean.UserAddress;
import cx.study.auction.constants.Constants;
import cx.study.auction.service.UserAddressService;
import cx.study.auction.service.UserService;
import cx.study.auction.utils.DefaultNicknameUtil;
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
@RequestMapping("/UserRest")
public class UserRest {
    @Resource
    private UserService userService;
    @Resource
    private UserAddressService userAddressService;

    @RequestMapping("/login")
    public HttpResult<User> login(@RequestBody JsonObject json){
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        User login = userService.login(username, password);
        if (login != null){
            if (login.getStatus() == Constants.UserStatus.DISABLE){
                return new HttpResult<>(-1,"该账户已停用",login);
            }
            return new HttpResult<>(0,"登录成功",login);
        } else {
            return new HttpResult<>(1,"用户名或密码错误",null);
        }
    }

    @RequestMapping("/register")
    public HttpResult<User> register(@RequestBody JsonObject json){
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        int gender = json.get("gender").getAsInt();
        User user = userService.findByUsername(username);
        if (user != null){
            return new HttpResult<>(1,"用户已存在",null);
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setGender(gender);
        user.setNickname(DefaultNicknameUtil.buildNickname());
        User register = userService.register(user);
        if (register != null){
            return new HttpResult<>(0,"注册成功",register);
        } else {
            return new HttpResult<>(1,"注册失败",null);
        }
    }
//    @RequestMapping("/addUserAddress")
//    public HttpResult<UserAddress> addUserAddress(@RequestBody JsonObject json){
//        int id = json.get("id").getAsInt();
//        User user = userService.finOne(id);
//        String address = json.get("address").getAsString();
//        String[] strings = address.split("@");
//        String name = strings[0];
//        String phone = strings[1];
//        String addr = strings[2] + strings[3];
//        UserAddress userAddress = new UserAddress();
//        userAddress.setName(name);
//        userAddress.setPhone(phone);
//        userAddress.setAddress(addr);
//        userAddress.setUser(user);
//        UserAddress add = userAddressService.add(userAddress);
//        if (add != null){
//            return new HttpResult<>(0,"地址添加成功",add);
//        } else {
//            return new HttpResult<>(1,"地址添加失败",null);
//        }
//    }

//    @RequestMapping("/userAddress")
//    public HttpResult<List<UserAddress>> findAllAddressByUser(@RequestBody JsonObject json) throws Exception{
//        int id = json.get("id").getAsInt();
//        User user = userService.finOne(id);
//        return new HttpResult<>(0,"",user.getAddresses());
//    }

    @RequestMapping("/recharge")
    public HttpResult<User> recharge(@RequestBody JsonObject json) throws Exception {
        int userId = json.get("userId").getAsInt();
        int money = json.get("money").getAsInt();
        User user = userService.finOne(userId);
        user.setAccount(user.getAccount()+ money);
        User update = userService.update(user);
        if (update != null){
            return new HttpResult<>(0,"充值成功",update);
        } else {
            return new HttpResult<>(1,"充值失败",null);
        }
    }
    @RequestMapping("/userInfo")
    public HttpResult<User> userInfo(@RequestBody JsonObject json) throws Exception {
        int id = json.get("id").getAsInt();
        User user = userService.finOne(id);
        if (user!= null){
            return new HttpResult<>(0,"请求成功",user);
        } else {
            return new HttpResult<>(1,"请求失败",null);
        }
    }

    @RequestMapping("/updateNickname")
    public HttpResult<User> updateNickname(@RequestBody JsonObject jsonObject){
        int id = jsonObject.get("id").getAsInt();
        String nickname = jsonObject.get("nickname").getAsString();
        User user = userService.updateNickname(id, nickname);
        return new HttpResult<>(0,"",user);
    }

    @RequestMapping("/updateGender")
    public HttpResult<User> updateGender(@RequestBody JsonObject jsonObject){
        int id = jsonObject.get("id").getAsInt();
        int gender = jsonObject.get("gender").getAsInt();
        User user = userService.updateGender(id, gender);
        return new HttpResult<>(0,"",user);
    }
}
