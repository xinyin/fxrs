package com.yzj.jep.app.base.constant;

import java.lang.annotation.*;

/**
 * @author : zhangfei
 * @date : 2020/7/15 10:25 上午
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String module() default "";

    /**
     * 描述
     */
    String description() default "";
}
