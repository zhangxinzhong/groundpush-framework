package com.groundpush.core.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.core.annotation.OperationLogDetail;
import com.groundpush.core.model.OperationLog;
import com.groundpush.core.model.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @author hengquan
 * @date 2019/9/21
 */
@Aspect
@Component
public class LogAspect {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     */
    @Pointcut("@annotation(com.groundpush.core.annotation.OperationLogDetail)")
    public void logAnnotation(){}


    @Before(value = "logAnnotation()")
    public void doBeforeAdvice(JoinPoint joinPoint) throws Throwable {
        System.out.println("我嘞个去");
        //当前时间
        long time = System.currentTimeMillis();
        //所有请求约定首个参数为对象
        Object object = joinPoint.getArgs()[0];
        //判断对象为何类型
        if(object instanceof Order){
            //取前操作人信息
        }
        //计算运行时间
        try {
            time = System.currentTimeMillis() - time;
        } finally {
            try {
                //方法执行完成后增加日志
                addOperationLog(joinPoint,time);
            }catch (Exception e){
                System.out.println("LogAspect 操作失败：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("logAnnotation()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }

    private void addOperationLog(JoinPoint joinPoint,long time) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        operationLog.setRunTime(time);
        operationLog.setArgs(objectMapper.writeValueAsString(joinPoint.getArgs()));
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setOperId("操作人ID");
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);
        if(annotation != null){
            operationLog.setLevel(annotation.level());
            operationLog.setDescribe(getDetail(((MethodSignature)joinPoint.getSignature()).getParameterNames(),joinPoint.getArgs(),annotation));
            operationLog.setOperationType(annotation.operationType().getValue());
            operationLog.setOperationUnit(annotation.operationUnit().getValue());
        }
        //TODO 这里保存日志
        System.out.println("记录日志:" + operationLog.toString());
//        operationLogService.insert(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     * @param argNames 方法参数名称数组
     * @param args 方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, OperationLogDetail annotation){

        Map<Object, Object> map = new HashMap<>(4);
        for(int i = 0;i < argNames.length;i++){
            map.put(argNames[i],args[i]);
        }

        String detail = annotation.detail();
        try {
            detail = "'" + "#{currentUserName}" + "'=》" + annotation.detail();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return detail;
    }


}
