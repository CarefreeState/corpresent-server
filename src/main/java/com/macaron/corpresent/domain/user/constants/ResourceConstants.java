package com.macaron.corpresent.domain.user.constants;

import java.util.concurrent.TimeUnit;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-15
 * Time: 18:17
 */
public interface ResourceConstants {

    String RESOURCE_READ_WRITE_LOCK = "resourceReadWriteLock";

    String USER_RESOURCE_SET = "userResourceSet:";
    Long USER_RESOURCE_SET_TIMEOUT = 1L;
    TimeUnit USER_RESOURCE_SET_TIMEUNIT = TimeUnit.DAYS;

}
