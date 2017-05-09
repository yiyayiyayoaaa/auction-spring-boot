package cx.study.auction.aspect;

import cx.study.auction.bean.HttpResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * Created by chengxiao on 2017/5/9.
 */
@Aspect
@Component
public class HttpAspect {

    private static  final Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    @Pointcut("execution(public * cx.study.auction.controller.*.*(..))")
    public void point(){

    }

    @Before("point()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url={}",request.getRequestURL());
        logger.info("ip={}",request.getRemoteAddr());
        logger.info("method={}",request.getMethod());
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+ joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "point()",returning = "object")
    public void after(Object object) throws IOException {
        logger.info("response={}",object);
        if (object instanceof HttpResult){
            switch (((HttpResult) object).getCode()){
                case -2:
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    HttpServletResponse response = attributes.getResponse();

                    break;
            }
        }
    }
}
