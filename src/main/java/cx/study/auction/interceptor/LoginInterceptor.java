package cx.study.auction.interceptor;

import cx.study.auction.bean.Admin;
import cx.study.auction.exception.NotLoginException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * Created by chengxiao on 2017/5/9.
 */
@WebServlet
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        if (uri.equals("/auction/login") || uri.equals("/auction/")){
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null){
            PrintWriter writer = httpServletResponse.getWriter();
            writer.print("<html><script type='text/javascript'> top.location.href='/auction'</script></html>");
            httpServletResponse.setContentType("text/html");
            //httpServletResponse.sendRedirect("/auction");
            writer.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
