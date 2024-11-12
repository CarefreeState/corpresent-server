package com.macaron.corpresent.domain.auth.service;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 16:41
 */
public interface EmailIdentifyService {

    String sendIdentifyingCode(String identifyType, String email);

    void validateEmailCode(String identifyType, String email, String code);

}
