package com.macaron.corpresent.domain.auth.constants;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 16:51
 */
public interface AuthConstants {

    Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    String EMAIL_LOGIN_IDENTIFY_TYPE = "login"; // 邮箱验证类型：登录
    String EMAIL_REGISTER_IDENTIFY_TYPE = "register"; // 邮箱验证类型：注册

    String LOGIN_FAIL_CNT_KEY = "loginFailCnt:"; // 登录失败次数

    Integer EMAIL_IDENTIFY_CODE_LENGTH = 6; // 验证码长度

    String EMAIL_IDENTIFY_CODE_MAP = "emailIdentifyCodeMap:%s:%s"; // emailIdentifyCodeMap:emailType:email
    Long EMAIL_IDENTIFY_TIMEOUT = 5L; // 过期时间分钟数
    Long EMAIL_IDENTIFY_RATE_LIMIT = 1L; // 限制某段时间内只能发一次
    TimeUnit EMAIL_IDENTIFY_TIMEUNIT = TimeUnit.MINUTES; // 单位

    String EMAIL_IDENTIFY_CODE_KEY = "captchaCode"; // map key 验证码
    String EMAIL_IDENTIFY_CODE_CNT_KEY = "captchaCodeCnt:"; // map key 验证码生成次数
    Integer EMAIL_IDENTIFY_CODE_MAX_RETRY_COUNT = 5;

    String VALIDATE_KEY = "validateKey:";
    String VALIDATE_EMAIL_CODE_KEY = "validateEmail:%s:%s";
    String VALIDATE_PASSWORD_KEY = "validatePassword:";
    Integer VALIDATE_MAX_RETRY_COUNT = 5;
    Long VALIDATE_BLOCKED_TIMEOUT = 10L;
    TimeUnit VALIDATE_BLOCKED_TIMEUNIT = TimeUnit.MINUTES;

    String EMAIL_BLOCKED_USER = "emailBlockedUser:"; // 暂时禁止发邮件的用户
    Long EMAIL_BLOCKED_TIMEOUT = 1L;
    TimeUnit EMAIL_BLOCKED_TIMEUNIT = TimeUnit.DAYS;

}
