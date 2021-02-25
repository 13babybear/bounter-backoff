package cn.bounter.backoff.annotation;


import cn.bounter.backoff.entity.BackoffRecord;
import cn.bounter.backoff.enums.BackoffStatusEnum;
import cn.bounter.backoff.service.BackoffService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class BackoffableAspect {

    /** 计数器 */
    private final ThreadLocal<Integer> counter = new ThreadLocal<Integer>();

    @Autowired
    private BackoffService backoffService;


    @Around("@annotation(backoffable)")
    public Object retry(ProceedingJoinPoint point, Backoffable backoffable) throws Throwable {
        Object retVal = null;

        try {
            retVal = point.proceed();
        } catch (Exception e) {
            //获取请求的类、方法、参数以及参数类型
            MethodSignature methodSignature = (MethodSignature)point.getSignature();
            Method method = methodSignature.getMethod();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            String param = null;
            String paramType = null;
            if (point.getArgs() != null && point.getArgs().length > 0) {
                //目前仅支持单参数接口
                param = JSON.toJSONString(point.getArgs()[0]);
                paramType = backoffable.value()[0].getName();
            }

            Retryable retryable = method.getAnnotation(Retryable.class);
            //如果没有重试注解或引发重试的异常是被排除的异常
            if (retryable == null || (retryable.exclude() != null && Arrays.asList(retryable.exclude()).contains(e.getClass()))) {
                //快速失败，直接补偿
                backoff(className, methodName, param, paramType, e.getMessage());
            } else {
                //等待重试次数全部失败再进行补偿
                log.info("接口{}#{}调用失败，执行一次重试", className, methodName);
                int retryTimes = retryable.maxAttempts();
                if (counter.get() == null) {
                    counter.set(retryTimes - 1);
                } else {
                    counter.set(counter.get() - 1);
                }
                if (counter.get() == 0) {
                    //计数器重置
                    counter.remove();
                    log.info("重试全部失败！");
                    backoff(className, methodName, param, paramType, e.getMessage());
                }
            }
            throw e;
        }

        return retVal;
    }

    private void backoff(String className, String methodName, String param, String paramType, String errorMsg) {
        BackoffRecord backoffRecord = new BackoffRecord().setClassName(className)
                                                         .setMethodName(methodName)
                                                         .setParam(param)
                                                         .setParamType(paramType)
                                                         .setErrorMsg(errorMsg)
                                                         .setStatus(BackoffStatusEnum.N.getValue())
                                                         .setCreateTime(new Date());
        //模拟推送数据到补偿中心
        backoffService.save(backoffRecord);
        log.info("推送一条快照数据到补偿中心：[{}]", JSON.toJSONString(backoffRecord));
    }
}
