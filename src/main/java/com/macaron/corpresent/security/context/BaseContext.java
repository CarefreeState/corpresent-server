package com.macaron.corpresent.security.context;

import com.macaron.corpresent.common.util.thread.ThreadLocalMapUtil;
import com.macaron.corpresent.jwt.UserHelper;

/**
 * @author cattleYuan
 * @date 2024/1/18
 */
public class BaseContext {

    private final static String USER_RECORD = "userRecord";

    public static void setCurrentUser(UserHelper userHelper) {
        ThreadLocalMapUtil.set(USER_RECORD, userHelper);
    }

    public static UserHelper getCurrentUser() {
        return ThreadLocalMapUtil.get(USER_RECORD, UserHelper.class);
    }

    public static void removeCurrentUser() {
        ThreadLocalMapUtil.remove(USER_RECORD);
    }

}
