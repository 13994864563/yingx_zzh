package com.baizhi.aspect;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Configuration   //说明这是一个配置类
@Aspect         //说明他是一个切面类
public class LogAspect {

    @Resource
    HttpServletRequest request;

    @Resource
    private LogMapper logMapper;

    @Resource
    private LogService logService;

    //环绕通知
    @Around("@annotation(com.baizhi.annotation.AddLog)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){

        //谁  时间  操作  成功

        //获取用户操作
        Admin admin = (Admin) request.getSession().getAttribute("admin1");

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();

        //获取方法名
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        //获取注解
        AddLog addLog = method.getAnnotation(AddLog.class);

        //获取注解对应的属性值
        String value = addLog.value();

        String message = null;
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
            message = "success";
        } catch (Throwable throwable) {
            message = "error";
        }

        Log log = new Log(UUID.randomUUID().toString(),admin.getUsername(),new Date(),methodName+" ("+value+")",message);
        System.out.println(log);
        logService.add(log);
        return result;
    }
}
