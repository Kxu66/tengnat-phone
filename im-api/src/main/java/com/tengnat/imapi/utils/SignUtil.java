package com.tengnat.imapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SignUtil {
    private final static Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static MessageDigest messageDigest;
    private final static Logger logger = LoggerFactory.getLogger(SignUtil.class);
    private final static String CHARSET = "UTF-8";
    private final static Charset DEFAULT_ENCODING = Charset.forName(CHARSET);

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            messageDigest = null;
            logger.error("Failed to init SHA1 BASE64_ENCODER. ", e);
        }
    }

    private static byte[] generateSHA1Data(byte[] data) {
        if (messageDigest != null && data != null && data.length > 0) {
            return messageDigest.digest(data);
        }
        return null;
    }

    private static byte[] generateSHA1Data(String data) {
        return generateSHA1Data(data.getBytes(DEFAULT_ENCODING));
    }

    private static String encode(byte[] bytes) {
        return BASE64_ENCODER.encodeToString(bytes);
    }

    private static String generateSHA1Base64String(String data) {
        return encode(generateSHA1Data(data));
    }

    public static String sign(String url, String body, String secretKey) {
        Map<String, String> params = getParamsFromUrl(url);
        return sign(params, body, secretKey);
    }

    public static String sign(Map<String, String> params, String body, String secretKey) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        String joinedParams = joinRequestParams(params, body, secretKey, keys);
        return generateSHA1Base64String(joinedParams);
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> requestParams = new HashMap<>();
        try {
            String fullUrl = URLDecoder.decode(url, CHARSET);
            String[] urls = fullUrl.split("\\?");
            if (urls.length == 2) {
                String[] paramArray = urls[1].split("&");
                for (String param : paramArray) {
                    String[] params = param.split("=");
                    if (params.length == 2) {
                        requestParams.put(params[0], params[1]);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("URL error", e);
        }
        return requestParams;
    }

    private static String joinRequestParams(Map<String, String> params, String body, String secretKey, String[] sortedKes) {
        StringBuilder sb = new StringBuilder(secretKey);
        for (String key : sortedKes) {
            if (!"sign".equals(key)) {
                String value = params.get(key);
                if (isNotEmpty(key) && isNotEmpty(value)) {
                    sb.append(key).append(value);
                }
            }
        }
        if (body != null) {
            sb.append(body);
        }
        sb.append(secretKey);
        return sb.toString();
    }

    private static boolean isNotEmpty(String s) {
        return null != s && !"".equals(s);
    }
}
