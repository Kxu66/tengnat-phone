package com.tengnat.assistant.data.resp;

import java.io.Serializable;

public class InvokeResult implements Serializable {

    private static final long serialVersionUID = -6510483143427033836L;
    /**
     * 请求成功
     */
    public static final int SUCCESS_CODE = 10000;

    /**
     * 请求失败
     */
    public static final int FAIL_CODE = 10001;

    /**
     * 没有登录
     */
    public static final int NO_LOGIN = 10005;

    /**
     * 没权限 不允许访问
     */
    public static final int NO_ALLOW = 10006;

    /**
     * 账号或密码错误
     */
    public static final int LOGIN_ERROR = 10007;

    /**
     * sso-sign登录凭证过期
     */
    public static final int LOGIN_EXPIRED = 10008;

//    public static final int LOGIN_EXPIRED = 10401;
    /**
     * 全局返回码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * @param code    全局返回码
     * @param message 提示信息
     */
    public InvokeResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功 <br />
     * code: 10000 <br />
     * message: success
     *
     * @return InvokeResult
     */
    public static InvokeResult success() {
        return new InvokeResult(SUCCESS_CODE, "请求成功");
    }

    /**
     * 失败 <br />
     * code: 10001 <br />
     * message: fail
     *
     * @return InvokeResult
     */
    public static InvokeResult fail() {
        return new InvokeResult(FAIL_CODE, "请求失败");
    }

    /**
     * 没登录的情况 <br />
     * code: 10005 <br />
     * message: noLogin
     *
     * @return InvokeResult
     */
    public static InvokeResult noLogin() {
        return new InvokeResult(NO_LOGIN, "请先登录系统");
    }

    /**
     * 登录凭证过期 <br />
     * code: 10008 <br />
     * message: loginExpired
     *
     * @return InvokeResult
     */
    public static InvokeResult LOGIN_EXPIRED() {
        return new InvokeResult(LOGIN_ERROR, "账号或密码错误");
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{code:" + code + ", message:" + message + "}";
    }
}
