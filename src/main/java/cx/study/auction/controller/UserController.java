package cx.study.auction.controller;

import com.google.gson.JsonObject;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.bean.User;
import cx.study.auction.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/user-list")
    public Map<String,Object> list(int start, int length, @RequestParam("search[value]") String query){
        Map<String,Object> map = new HashMap<>();
        Page<User> page = userService.findAll(start, length,query);
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return map;
    }

    @PostMapping("/user-stop")
    public HttpResult<String> userStop(Integer id){
        User user = userService.stop(id);
        return new HttpResult<>(0,"已停用",null);
    }

    @PostMapping("/user-start")
    public HttpResult<String> userStart(Integer id){
        User user = userService.start(id);
        return new HttpResult<>(0,"已启用",null);
    }
}
