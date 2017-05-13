package cx.study.auction.controller;

import cx.study.auction.bean.Admin;
import cx.study.auction.bean.Customer;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.constants.Constants;
import cx.study.auction.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
                if (admin.getStatus() == Constants.UserStatus.DISABLE){
                    request.setAttribute("error","该账号已停用！");
                    return "login";
                }
                Admin mAdmin  = (Admin) admin.clone();
                session.setAttribute("admin",mAdmin);
                admin.setUpdateTime(new Date().getTime());
                admin.setIp(getIpAddr(request));
                adminService.update(admin);
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

    @RequestMapping("/update-password")
    @ResponseBody
    public HttpResult<String> updatePassword(HttpSession session,String oldPassword,String password){
        Admin admin = (Admin) session.getAttribute("admin");
        Integer id = admin.getId();
        Admin update = adminService.updatePassword(id,oldPassword,password);
        if (update != null) {
            return new HttpResult<>(0, "修改成功", null);
        }
        return new HttpResult<>(1, "修改失败", null);
    }

    @RequestMapping("/reset-password")
    @ResponseBody
    public HttpResult<String> resetPassword(Integer id){
        Admin update = adminService.resetPassword(id);
        if (update != null) {
            return new HttpResult<>(0, "修改成功", null);
        }
        return new HttpResult<>(1, "修改失败", null);

    }

    @RequestMapping("/admin-start")
    @ResponseBody
    public HttpResult<String> start(Integer id){
        adminService.updateStatus(id, Constants.UserStatus.USER_ING);
        return new HttpResult<>(0,"",null);
    }

    @RequestMapping("/admin-stop")
    @ResponseBody
    public HttpResult<String> stop(Integer id){
        adminService.updateStatus(id, Constants.UserStatus.DISABLE);
        return new HttpResult<>(0,"",null);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(SessionStatus sessionStatus, HttpSession session) throws IOException {
        session.removeAttribute("admin");
        sessionStatus.setComplete();
    }

    @RequestMapping("/delete-batch-admin")
    @ResponseBody
    public HttpResult<String> deleteBatch(String ids){
        adminService.deleteBatch(ids);
        return new HttpResult<>(0,"",null);
    }
    @RequestMapping("/delete-admin")
    @ResponseBody
    public HttpResult<String> delete(String id){
        adminService.delete(id);
        return new HttpResult<>(0,"",null);
    }

    @RequestMapping("/add-admin")
    @ResponseBody
    public HttpResult<String> add(String username,int level){
        adminService.add(username,level);
        return new HttpResult<>(0,"",null);
    }
}
