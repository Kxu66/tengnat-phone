package com.tengnat.assistant.data.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientUtil {
    public HttpRes doPost(HttpPost httpPost, String url, UrlEncodedFormEntity entity, String charset){
//        HttpClient httpClient = null;
        try{
//            httpClient = new SSLClient();
            // 创建Httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try {
                //设置参数
                httpPost.setEntity(entity);
                HttpResponse response =  httpClient.execute(httpPost);
                return new HttpRes(response);
            } finally {
                HttpClientUtils.closeQuietly(httpClient);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
