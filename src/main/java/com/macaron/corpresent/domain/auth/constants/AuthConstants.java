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

    String JWT_SUBJECT = "登录认证";

    Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    String EMAIL_IDENTIFY_CODE_MAP = "emailIdentifyCodeMap:%s:%s"; // emailIdentifyCodeMap:emailType:email
    Long EMAIL_IDENTIFY_RATE_LIMIT = 1L; // 限制某段时间内只能发一次
    Long EMAIL_IDENTIFY_TIMEOUT = 5L; // 过期时间
    TimeUnit EMAIL_IDENTIFY_TIMEUNIT = TimeUnit.MINUTES; // 单位
    Integer EMAIL_IDENTIFY_CODE_LENGTH = 6; // 验证码长度

    String EMAIL_IDENTIFY_CODE_KEY = "captchaCode"; // map key 验证码
    String EMAIL_IDENTIFY_CODE_COUNT_KEY = "captchaCodeCount"; // map key 验证码生成次数
    Integer EMAIL_IDENTIFY_CODE_MAX_RETRY_COUNT = 5;

    String VALIDATE_FAIL_COUNT = "validateFailCount:";
    String VALIDATE_EMAIL_CODE_KEY = "validateEmail:%s:%s";
    String VALIDATE_PASSWORD_KEY = "validatePassword:";
    Long VALIDATE_BLOCKED_TIMEOUT = 10L;
    TimeUnit VALIDATE_BLOCKED_TIMEUNIT = TimeUnit.MINUTES;
    Integer VALIDATE_MAX_RETRY_COUNT = 5;

    String EMAIL_BLOCKED_USER = "emailBlockedUser:"; // 暂时禁止发邮件的用户
    Long EMAIL_BLOCKED_TIMEOUT = 1L;
    TimeUnit EMAIL_BLOCKED_TIMEUNIT = TimeUnit.DAYS;

}
