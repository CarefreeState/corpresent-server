package com.macaron.corpresent.domain.user.annotation;

import java.lang.annotation.*;

/**
 * Created With Intellij IDEA
 * User: 马拉圈
 * Date: 2024-11-15
 * Time: 19:07
 * Description: 标注代表删除用户资源缓存，若 isSpecified 为 true 则，第一个参数若为 Long，则认为是 userId，删除指定的用户的资源缓存
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceClear {

    boolean isSpecified() default false;

}
