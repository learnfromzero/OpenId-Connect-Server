package com.shaun.commons.controller;

import com.shaun.authorize.entity.UserAccount;
import com.shaun.commons.entity.LogEntity;
import com.shaun.commons.annotation.BussinessLog;
import com.shaun.commons.service.LogService;
import com.shaun.commons.util.CusAccessObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author luotao
 * @Description 记录日志的aop，先使用pointcut expression定义一个切面
 * @Date Created on 2017/11/21.
 */
@Aspect
@Component
public class LogAction {
    private Log log = LogFactory.getLog(LogAction.class);

    @Autowired
    private LogService logService;
    /**
     * 定义切面，使用注解BussinessLog注解的方法都会被拦截
     */
    @Pointcut("@annotation(com.shaun.commons.annotation.BussinessLog)")
    public void cutService(){

    }

    /**
     * 配置环绕通知
     * 首先获取拦截的方法对象。通过方法对象获取拦截的标注对象，获取注解参数，记录日志
     * @param point
     * @return
     */
    @Around(value = "cutService()")
    public Object around(ProceedingJoinPoint point)throws Throwable{
        Object object = null;
        //获取拦截的方法
        Signature sig = point.getSignature();
        if(!(sig instanceof MethodSignature)){
            throw new IllegalArgumentException("该注解只能使用在方法上！");
        }
        MethodSignature msig = (MethodSignature) sig;
        //接下来通过拦截的方法名获取使用标注的方法
        Method method = null;
        try{
            method = point.getTarget().getClass().getMethod(msig.getName(),msig.getParameterTypes());
        }catch (NoSuchMethodException e){
            log.error(e);
        }
        //如果拦截的方法不为空，说明有方法使用了BussinessLog注解
        if (method!=null){
            //再次判断方法是否使用BussinessLog注解
            if(method.isAnnotationPresent(BussinessLog.class)){
                //获取方法使用的注解实例
                BussinessLog annotation = method.getAnnotation(BussinessLog.class);
                LogEntity logEntity = new LogEntity();
                logEntity.setModule(annotation.module());
                logEntity.setMethod(annotation.methoed());
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                UserAccount userAccount = (UserAccount) request.getSession().getAttribute("userAccountInfo");
                logEntity.setUserId(userAccount.getUserId());
                logEntity.setIp(CusAccessObjectUtil.getIpAddress(request));
                try {
                    object = point.proceed();
                    logEntity.setCommit("执行成功！");
                    logService.saveLog(logEntity);
                }catch (Throwable e){
                    logEntity.setCommit("发送异常！");
                    logService.saveLog(logEntity);
                }
            } else {
                object = point.proceed();
            }
        }else {
            object = point.proceed();
        }
        return object;
    }
}
