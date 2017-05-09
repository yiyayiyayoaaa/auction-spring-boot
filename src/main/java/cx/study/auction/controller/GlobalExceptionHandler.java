package cx.study.auction.controller;

import cx.study.auction.bean.HttpResult;
import cx.study.auction.exception.NotLoginException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
