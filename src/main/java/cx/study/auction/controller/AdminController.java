package cx.study.auction.controller;

import cx.study.auction.bean.Admin;
import cx.study.auction.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

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
}
