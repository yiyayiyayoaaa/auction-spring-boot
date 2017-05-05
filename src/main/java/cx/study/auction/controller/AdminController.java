package cx.study.auction.controller;

import cx.study.auction.bean.Admin;
import cx.study.auction.bean.Customer;
import cx.study.auction.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Controller
public class AdminController {

    @Resource
    private AdminService adminService;
    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, HttpServletRequest request) throws CloneNotSupportedException {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            Admin admin = adminService.login(username, password);
            if (admin != null){
                Admin mAdmin  = (Admin) admin.clone();
                session.setAttribute("admin",mAdmin);
                admin.setUpdateTime(new Date().getTime());
                admin.setIp(getIpAddr(request));
                adminService.AdminUpdate(admin);
                return "redirect:index";
            }
        }
        request.setAttribute("error","账号或密码错误！");
        return "login";
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @GetMapping("/admin-list")
    @ResponseBody
    public Map<String,Object> list(int start, int length, @RequestParam("search[value]") String query){
        Map<String,Object> map = new HashMap<>();
        Page<Admin> page = adminService.findAll(start, length,query);
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return map;
    }
}
