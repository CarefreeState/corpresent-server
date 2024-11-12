package com.macaron.corpresent.domain.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.model.vo.IdentifyTemplateVO;
import com.macaron.corpresent.domain.auth.service.EmailIdentifyService;
import com.macaron.corpresent.domain.auth.service.ValidateService;
import com.macaron.corpresent.email.model.po.EmailMessage;
import com.macaron.corpresent.email.sender.EmailSender;
import com.macaron.corpresent.redis.cache.RedisCache;
import com.macaron.corpresent.template.engine.HtmlEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.macaron.corpresent.common.enums.GlobalServiceStatusCode.*;
import static com.macaron.corpresent.domain.auth.constants.AuthConstants.*;
import static com.macaron.corpresent.email.enums.EmailTemplate.EMAIL_IDENTIFY;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 16:47
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailIdentifyServiceImpl implements EmailIdentifyService {

    private final HtmlEngine htmlEngine;

    private final EmailSender emailSender;

    private final RedisCache redisCache;

    private final ValidateService validateService;

    private void buildEmailAndSend(String email, String code) {
        // 封装 Email
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTitle(EMAIL_IDENTIFY.getTitle());
        emailMessage.setRecipient(email);
        // 构造模板消息
        IdentifyTemplateVO identifyTemplateVO = IdentifyTemplateVO.builder()
                .code(code)
                .timeout(EMAIL_IDENTIFY_TIMEUNIT.toMinutes(EMAIL_IDENTIFY_TIMEOUT))
                .build();
        String html = htmlEngine.builder()
                .append(EMAIL_IDENTIFY.getTemplate(), identifyTemplateVO)
                .build();
        emailMessage.setContent(html);
        // 发送模板消息
        emailSender.send(emailMessage);
    }

    @Override
    public String sendIdentifyingCode(String identifyType, String email) {
        // 检查是否为风控用户, 如果是直接跳过
        if (redisCache.getCacheObject(EMAIL_BLOCKED_USER + email).isPresent()) {
            String message = String.format("邮箱 %s 已被风控，%d 小时后解封", email, EMAIL_BLOCKED_TIMEUNIT.toHours(EMAIL_BLOCKED_TIMEOUT));
            // 申请验证码次数用尽
            throw new GlobalServiceException(message, EMAIL_CAPTCHA_CODE_COUNT_EXHAUST);
        }

        // 拼接验证码在 redis 缓存的 map 的 key
        String redisKey = String.format(EMAIL_IDENTIFY_CODE_MAP, identifyType, email);
        // 验证一下一分钟以内发过了没有
        long ttl = redisCache.getKeyTTL(redisKey); // 小于 0 则代表没有到期时间或者不存在，允许发送
        if (ttl > TimeUnit.MINUTES.toMillis((EMAIL_IDENTIFY_TIMEOUT - EMAIL_IDENTIFY_RATE_LIMIT))) {
            String message = String.format("申请太频繁, 请在 %d 分钟后再重新申请", EMAIL_IDENTIFY_RATE_LIMIT);
            throw new GlobalServiceException(message, EMAIL_SEND_FAIL);
        }

        // 获取缓存
        String code = RandomUtil.randomNumbers(EMAIL_IDENTIFY_CODE_LENGTH);
        redisCache.getCacheMap(redisKey).ifPresentOrElse(cache -> {
                    long curRetryCount = redisCache.decrementCacheMapNumber(redisKey, EMAIL_IDENTIFY_CODE_CNT_KEY);
                    if (curRetryCount <= 0) {
                        redisCache.setCacheObject(EMAIL_BLOCKED_USER + email, email, EMAIL_BLOCKED_TIMEOUT, EMAIL_BLOCKED_TIMEUNIT);
                        String message = String.format("邮箱 %s 已被风控，%d 小时后解封", email, EMAIL_BLOCKED_TIMEUNIT.toHours(EMAIL_BLOCKED_TIMEOUT));
                        // 申请验证码次数用尽
                        throw new GlobalServiceException(message, EMAIL_CAPTCHA_CODE_COUNT_EXHAUST);
                    }
                    redisCache.execute(() -> {
                        redisCache.setCacheMapValue(redisKey, EMAIL_IDENTIFY_CODE_KEY, code);
                        redisCache.expire(redisKey, EMAIL_IDENTIFY_TIMEOUT, EMAIL_IDENTIFY_TIMEUNIT);
                    });
                }, () -> {
                    // 如果 redis 没有对应 key 值，初始化
                    Map<String, Object> captchaCodeMap = new HashMap<>();
                    captchaCodeMap.put(EMAIL_IDENTIFY_CODE_CNT_KEY, EMAIL_IDENTIFY_CODE_MAX_RETRY_COUNT);
                    captchaCodeMap.put(EMAIL_IDENTIFY_CODE_KEY, code);
                    redisCache.setCacheMap(redisKey, captchaCodeMap, EMAIL_IDENTIFY_TIMEOUT, EMAIL_IDENTIFY_TIMEUNIT);
                }
        );
        // 发送模板消息
        buildEmailAndSend(email, code);
        return code;
    }

    @Override
    public void validateEmailCode(String identifyType, String email, String code) {
        validateService.validate(String.format(VALIDATE_EMAIL_CODE_KEY, identifyType, email), () -> {
            String redisKey = String.format(EMAIL_IDENTIFY_CODE_MAP, identifyType, email);
            return redisCache.getCacheMapValue(redisKey, EMAIL_IDENTIFY_CODE_KEY).orElseThrow(() -> {
                String message = String.format("不存在邮箱 %s 的相关记录", email);
                return new GlobalServiceException(message, GlobalServiceStatusCode.EMAIL_NOT_EXIST_RECORD);
            }).equals(code);
        }, GlobalServiceStatusCode.USER_CAPTCHA_CODE_ERROR);
    }
}
