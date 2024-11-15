package com.macaron.corpresent.locator;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;

import java.lang.reflect.Constructor;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-09-05
 * Time: 11:47
 */
public abstract class CustomServiceLocatorFactoryBean extends ServiceLocatorFactoryBean {

    protected abstract void init();

    @Override
    public void afterPropertiesSet() {
        init();
        // 自定义异常
        super.setServiceLocatorExceptionClass(GlobalServiceException.class);
        // 执行原初始化方法
        super.afterPropertiesSet();
    }

    @Override
    protected Exception createServiceLocatorException(Constructor<Exception> exceptionConstructor, BeansException cause) {
        return new GlobalServiceException(GlobalServiceStatusCode.REQUEST_NOT_VALID);
    }
}
