package com.macaron.corpresent.common.util.http;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.media.MediaUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-09-22
 * Time: 23:51
 */
public class HttpServletUtil {

    public static Optional<ServletRequestAttributes> getAttributes() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    /**
     * 获取本次请求的 HttpServletRequest 对象
     */
    public static HttpServletRequest getRequest() {
        return getAttributes().map(ServletRequestAttributes::getRequest).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.REQUEST_NOT_VALID));
    }

    /**
     * 获取本次请求的 HttpServletResponse 对象
     */
    public static HttpServletResponse getResponse() {
        return getAttributes().map(ServletRequestAttributes::getResponse).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.REQUEST_NOT_VALID));
    }

    public static void returnBytes(byte[] bytes, HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 写入数据
            if(Objects.nonNull(bytes)) {
                // 设置响应内容类型（用同一个 inputStream 会互相影响）
                response.setContentType(MediaUtil.getContentType(bytes));
                // 指定字符集
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            throw new GlobalServiceException(e.getMessage());
        }
    }

    public static void returnBytes(String downloadName, byte[] bytes, HttpServletResponse response) {
        // 在设置内容类型之前设置下载的文件名称
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; fileName=%s", HttpRequestUtil.encodeString(downloadName)));
        returnBytes(bytes, response);
    }

    public static String getHost(HttpServletRequest request) {
        return String.format("%s://%s", request.getScheme(), request.getHeader(HttpHeaders.HOST));
    }

    public static String getBaseUrl(HttpServletRequest request, String... uris) {
        String uri = Arrays.stream(uris).filter(StringUtils::hasText).collect(Collectors.joining());
        return getHost(request) + uri;
    }

    private static boolean ipAddressIsValid(String ipAddress) {
        return !StringUtils.hasText(ipAddress) || "unknown".equalsIgnoreCase(ipAddress);
    }

    /**
     * 获取请求真实IP地址
     */
    public static String getIP(HttpServletRequest request) {
        //通过 HTTP 代理服务器转发时添加
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddressIsValid(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddressIsValid(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddressIsValid(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            // 从本地访问时根据网卡取本机配置的IP
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                try {
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    throw new GlobalServiceException(e.getMessage());
                }
            }
        }
        // 通过多个代理转发的情况，第一个IP为客户端真实IP，多个IP会按照','分割
        if (StringUtils.hasText(ipAddress) && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


}
