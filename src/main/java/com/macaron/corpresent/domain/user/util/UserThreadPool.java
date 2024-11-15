package com.macaron.corpresent.domain.user.util;

import com.macaron.corpresent.common.util.juc.ThreadPoolUtil;
import com.macaron.corpresent.email.aspect.EmailAsynchronousThreadPool;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-15
 * Time: 13:49
 */
public class UserThreadPool {

    private final static String THREAD_NAME = "userThread";

    private final static ThreadPoolExecutor USER_THREAD_POOL;

    static {
        USER_THREAD_POOL = ThreadPoolUtil.getIoTargetThreadPool(THREAD_NAME);
    }

    public static void submit(Runnable... tasks) {
        Arrays.stream(tasks).forEach(UserThreadPool::submit);
    }

    public static void submit(Runnable runnable) {
        USER_THREAD_POOL.submit(runnable);
    }

}
