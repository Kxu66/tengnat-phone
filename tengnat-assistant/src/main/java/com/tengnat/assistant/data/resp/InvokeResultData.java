package com.tengnat.assistant.data.resp;

public class InvokeResultData extends InvokeResult {

    private static final long serialVersionUID = -8333872611454369244L;

    /**
     * 数据
     */
    private Object data;


    /**
     * @param code    全局返回码
     * @param message 提示信息
     */
    public InvokeResultData(Integer code, String message) {
        super(code, message);
    }

    /**
     * @param code    全局返回码
     * @param message 提示信息
     * @param data    数据
     */
    public InvokeResultData(Integer code, String message, Object data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 成功 <br />
     * code: 10000 <br />
     * message: success <br />
     * data: Object
     *
     * @param data 数据
     * @return InvokeResultData
     */
    public static InvokeResultData success(Object data) {
        return new InvokeResultData(SUCCESS_CODE, "成功", data);
    }

    /**
     * 失败 <br />
     * code: 10001 <br />
     * message: fail
     *
     * @param data 数据
     * @return InvokeResultData
     */
    public static InvokeResultData fail(Object data) {
        return new InvokeResultData(FAIL_CODE, "失败", data);
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
