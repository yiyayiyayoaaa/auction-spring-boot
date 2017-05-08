package cx.study.auction.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by chengxiao on 2017/5/9.
 */
@WebServlet
public class LoginInterceptor implements HandlerInterceptor{
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    public LoginInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("URI1："+ requestURI);
        logger.info("IP1：" + request.getRemoteAddr());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String requestURI = httpServletRequest.getRequestURI();
        logger.info("URI2：",requestURI);
        logger.info("IP2：",httpServletRequest.getRemoteAddr());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
