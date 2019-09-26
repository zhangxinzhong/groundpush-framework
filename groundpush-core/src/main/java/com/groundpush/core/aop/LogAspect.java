package com.groundpush.core.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.enums.OperationType;
import com.groundpush.core.model.*;
import com.groundpush.core.repository.OperationLogRepository;
import com.groundpush.core.utils.LoginUtils;
import com.groundpush.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA
 *
 * @author hengquan
 * @date 2019/9/21
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private LoginUtils loginUtils;
    @Resource
    private SessionUtils sessionUtils;
    @Resource
    private OperationLogRepository operationLogRepository;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     */
    @Pointcut("@annotation(com.groundpush.core.annotation.OperationLogDetail)")
    public void logAnnotation() {
    }

    @Before(value = "logAnnotation()")
    public void doBeforeAdvice(JoinPoint joinPoint) throws Throwable {
        log.info("进去前置任务");
        //当前时间
        long time = System.currentTimeMillis();
        try {
            //方法执行完成后增加日志
            addOperationLog(joinPoint, time, null);
        } catch (Exception e) {
            log.info("LogAspect 操作失败：{}", e.getMessage());
            e.printStackTrace();
            throwss(joinPoint,e);
        }
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing(pointcut = "logAnnotation()", throwing = "exception")
    public void throwss(JoinPoint joinPoint, Exception exception) throws Throwable {
        log.info("方法异常时执行");
        //当前时间
        long time = System.currentTimeMillis();
        try {
            //方法执行完成后增加日志
            addOperationLog(joinPoint, time, exception);
        } catch (Exception e) {
            log.info("LogAspect 操作失败：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 方法体
     *
     * @param joinPoint
     * @param time
     * @throws JsonProcessingException
     */
    private void addOperationLog(JoinPoint joinPoint, long time, Exception exception) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        //存运行时间
        operationLog.setRunTime((System.currentTimeMillis() - time) + "");
        //存请求参数
        operationLog.setArgs(objectMapper.writeValueAsString(joinPoint.getArgs()));
        //存方法名
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        //存异常描述
        if (exception != null) {
            operationLog.setExceptionDetail(exception.toString());
        }
        //获取自定义注解类里面的参数
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);
        if (annotation != null) {
            //获取日志类型
            String operationType = annotation.operationType().getValue();
            //存日志类型
            operationLog.setOperationType(operationType);
            //获取日志类型描述
            OperationType detailType = OperationType.valueOf(operationType);
            String detail = detailType.getValue();
            //存日志类型描述
            operationLog.setOperationDetail(detail);
            //获取类型（0-APP，1-PC）
            int type = annotation.type();
            //存类型
            operationLog.setType(type);
            //根据不同类型获取创建人信息
            if (type == 0) {
                Optional<Customer> login = loginUtils.getLogin();
                Customer customer = login.get();
                //存创建人ID
                operationLog.setCreatedBy(customer.getCustomerId());
            } else {
                Optional<LoginUserInfo> login = sessionUtils.getLogin();
                LoginUserInfo loginUserInfo = login.get();
                User user = loginUserInfo.getUser();
                //存创建人ID
                operationLog.setCreatedBy(user.getUserId());
            }
        }
        // 这里保存日志
        log.info("记录日志:{}", operationLog.toString());
        operationLogRepository.createOperationLog(operationLog);
    }

}
