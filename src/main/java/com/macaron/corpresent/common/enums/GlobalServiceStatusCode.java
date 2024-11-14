package com.macaron.corpresent.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <span>
 * <h3> global service status code </h3>
 * Please note that status code definitions are module-specific
 * and do not occupy other business modules when defining them.
 * </span>
 *
 */
@Getter
@AllArgsConstructor
public enum GlobalServiceStatusCode {

    /* 成功, 默认200 */
    SYSTEM_SUCCESS(200, "操作成功"),

    /* 系统错误 500 - 1000 */
    SYSTEM_SERVICE_FAIL(-4396, "操作失败"),
    SYSTEM_SERVICE_ERROR(-500, "系统异常"),
    SYSTEM_TIME_OUT(-1, "请求超时"),

    /* 参数错误：1001～2000 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    PARAM_FAILED_VALIDATE(1005, "参数未通过验证"),

    REQUEST_NOT_VALID(1101, "请求无效"),

    /* 用户错误 2001-3000 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USER_ACCOUNT_REGISTER_ERROR(2010, "账号注册错误"),

    USER_TYPE_EXCEPTION(2101, "用户类别异常"),

    USER_NO_PERMISSION(2403, "用户无权限"),
    USER_UN_AUTHORIZED(2404, "暂未登录或 token 无效"),

    USER_IDENTIFY_CODE_ERROR(2500, "验证码错误"),
    USER_USERNAME_PASSWORD_ERROR(2501, "用户名或密码错误"),

    /* 邮箱错误 3001-4000 */
    EMAIL_PATTERN_ERROR(3001, "邮箱格式错误"),
    EMAIL_SENDER_NOT_EXISTS(3002, "邮箱发送器不存在"),
    EMAIL_SEND_FAIL(3003, "邮箱发送失败"),
    EMAIL_ATTACH_SEND_FAIL(3004, "邮箱附件发送失败"),

    EMAIL_NOT_EXIST_RECORD(3101, "邮箱不存在记录"),
    EMAIL_IDENTIFY_CODE_COUNT_EXHAUST(3102, "申请次数达到上限"),

    EMAIL_LOGIN_IDENTIFY_CODE_ERROR(3201, "邮箱登录验证码错误"),
    EMAIL_REGISTER_IDENTIFY_CODE_ERROR(3202, "邮箱注册验证码错误"),

    /* 用户权限体系错误 4001-5000 */
    RESOURCE_NOT_EXISTS(4001, "资源不存在"),
    RESOURCE_CATEGORY_NOT_EXISTS(4002, "资源分类不存在"),
    ROLE_NOT_EXISTS(4003, "角色不存在"),

    /* 文件资源错误 6001-7000 */
    FILE_RESOURCE_NOT_VALID(6001, "文件资源非法"),
    FILE_RESOURCE_TYPE_NOT_MATCH(6002, "文件资源类型不匹配"),

    /* WebSocket想关错误 7001-8000 */
    USER_NOT_ONLINE(7001, "用户不在线"),

    /* -------------- */;

    private final Integer code;
    private final String message;

}
