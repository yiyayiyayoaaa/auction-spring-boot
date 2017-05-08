package cx.study.auction.controller;

import cx.study.auction.bean.HttpResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by chengxiao on 2017/5/8.
 */

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    public HttpResult handler(Exception e){
        return new HttpResult<>(-1,e.getMessage(),null);
    }
}
