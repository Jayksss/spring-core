package com.springcore.web.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    /** 모든 컨트롤러 요청에 대해서 로그에 다음을 출력한다
     *
     * MethodName ::: index ::: URL ::: /index ::: Start
     * MethodName ::: index ::: URL ::: /index ::: End
     * */

    // 컨트롤러 전체에 적용
    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void allControllers() {}

    @Before("allControllers()")
    public void logBefore(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String method = joinPoint.getSignature().getName();   // 메서드명
        String url = request.getRequestURI();                 // 요청 URL
        String httpMethod = request.getMethod();              // GET, POST 등

        String contentType = request.getHeader("Accept");
        if (contentType != null && contentType.contains("text/html")) {
            log.info("MethodName ::: {} ::: URL ::: {} ::: Start", method, url);
        }
    }

    @After("allControllers()")
    public void logAfter(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String method = joinPoint.getSignature().getName();   // 메서드명
        String url = request.getRequestURI();                 // 요청 URL
        String httpMethod = request.getMethod();              // GET, POST 등

        String contentType = request.getHeader("Accept");
        if (contentType != null && contentType.contains("text/html")) {
            log.info("MethodName ::: {} ::: URL ::: {} ::: End", method, url);
        }
    }
}
