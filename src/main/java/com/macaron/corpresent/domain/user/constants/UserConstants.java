package com.macaron.corpresent.domain.user.constants;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 2:25
 */
public interface UserConstants {

    Long DEFAULT_ROLE = 2L; // 用户注册时默认的客户

    String SORT_USER_LOCK = "sortLockUser"; // 排序用户的时候需要用锁保证没有并发问题

}
