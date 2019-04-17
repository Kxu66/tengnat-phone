package com.tengnat.assistant.data.util;



import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;



public class HttpRes {
    private int status;
    private String entity;
    public HttpRes(int status, String entity) {
        super();
        this.status = status;
        this.entity = entity;
    }

    public HttpRes(HttpResponse response) throws Exception {
        this(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
//        this(response.getStatusLine().getStatusCode(), response.getEntity().toString());
    }

    public int getStatus() {
        return status;
    }
    public String getEntity() {
        return entity;
    }
    public JSONObject getEntityMap() {
        return JSONObject.parseObject(entity);
    }

    @Override
    public String toString() {
        return "status:[" + status + "]  message: [" + entity + "]";
    }
}
