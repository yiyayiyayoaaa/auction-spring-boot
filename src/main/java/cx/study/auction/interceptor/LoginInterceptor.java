package cx.study.auction.interceptor;

import cx.study.auction.bean.Admin;
import cx.study.auction.exception.NotLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        logger.info("登录验证url={}",httpServletRequest.getRequestURL());
        if ("mobile".equals(httpServletRequest.getHeader("_User"))){
            return true;
        }
        if (uri.equals("/auction/login") || uri.equals("/auction/")){
            return true;
        }

        HttpSession session = httpServletRequest.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null){
            logger.info("是否登录={}","未登录");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.print("<html><script type='text/javascript'> top.location.href='/auction'</script></html>");
            httpServletResponse.setContentType("text/html");
            //httpServletResponse.sendRedirect("/auction");
            writer.close();
            return false;
        }
        logger.info("是否登录={}","已登录");
        logger.info("登录名={}",admin.getUsername());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
