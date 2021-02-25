package cn.bounter.backoff.annotation;

import java.lang.annotation.*;

/**
 * 自定义补偿注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Backoffable {

    /**
     * 请求参数类型
     * @return
     */
    Class[] value() default {};
}