package com.zcy.nls.business.aspect;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Aspect
@Component
public class LogAspect {

//    @Resource
//    private SnowFlake snowFlake;

    /**
     * 定义一个切点
     */
    @Pointcut("execution(public * com.zcy..*Controller.*(..))")
    public void pointcut() {

    }

//    @Before("pointcut()")
//    public void doBefore(JoinPoint joinPoint) {
//        log.info("前置通知");
//    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MDC.put("LOG_ID", IdUtil.getSnowflakeNextIdStr());
        log.info("--------------------------环绕通知 开始--------------------------");
        long startTime = System.currentTimeMillis();
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Signature signature = proceedingJoinPoint.getSignature();
        String name = signature.getName();
        // 打印请求信息
        log.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        log.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
        log.info("远程地址: {}", request.getRemoteAddr());
        // 打印请求参数
        Object[] args = proceedingJoinPoint.getArgs();
        // LOG.info("请求参数: {}", JSONObject.toJSONString(args));
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        log.info("请求参数: {}", JSONObject.toJSONString(arguments, excludefilter));
        Object res = proceedingJoinPoint.proceed();
        log.info("返回结果: {}", JSONObject.toJSONString(res, excludefilter));
        log.info("--------------------------环绕通知结束 耗时{}ms--------------------------", System.currentTimeMillis() - startTime);
        return res;
    }

//    @After("pointcut()")
//    public void doAfter(JoinPoint joinPoint) {
//        log.info("后置通知");
//    }
}