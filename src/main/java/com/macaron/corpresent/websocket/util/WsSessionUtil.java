package com.macaron.corpresent.websocket.util;


import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.websocket.session.WsSessionMapper;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-04-26
 * Time: 13:59
 */
public class WsSessionUtil {

    public static int getConnectTotal(String prefix) {
        return WsSessionMapper.size(prefix);
    }

    public static List<String> getSessionKeys(String prefix) {
        return new ArrayList<>(WsSessionMapper.getKeys(prefix));
    }

    public static void close(Session session) {
        if(Objects.isNull(session)) {
            return;
        }
        try {
            synchronized (session) {
                if(session.isOpen()) {
                    session.close();
                }
            }
        } catch (IOException e) {
            throw new GlobalServiceException(e.getMessage());
        }
    }

    public static void refuse(String exceptionMessage) throws DeploymentException {
        throw new DeploymentException(exceptionMessage);
    }

    public static Map<String, String> getPathParameter(Session session) {
        return session.getPathParameters();
    }

    public static Map<String, Object> getUserProperties(Session session) {
        return session.getUserProperties();
    }

    public static String getQueryString(Session session) {
        return session.getQueryString();
    }

    public static Map<String, List<String>> getParameterMap(Session session) {
        return session.getRequestParameterMap();
    }

    public static URI getRequestURI(Session session) {
        return session.getRequestURI();
    }

}
