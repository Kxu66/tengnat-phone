package com.tengnat.assistant.data.third.netease;



import com.tengnat.assistant.data.properties.NeteaseProperties;
import com.tengnat.assistant.data.util.HttpClientUtil;
import com.tengnat.assistant.data.util.HttpRes;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class Netease {

    @Autowired
    private NeteaseProperties neteaseProperties;


    private static String NETEASE_URL ;
    private static String NETEASE_NONCE;
    private static String NETEASE_APPKEY;
    private static String NETEASE_APPSEC;
    private static String NETEASE_PASSWD;

    private List<ActionEntity> actions = new ArrayList<>();

    @PostConstruct
    public void doinit() {

        NETEASE_URL = this.neteaseProperties.getUrl();
        NETEASE_NONCE = this.neteaseProperties.getNonce();
        NETEASE_APPKEY = this.neteaseProperties.getAppkey();
        NETEASE_APPSEC = this.neteaseProperties.getAppsec();
        NETEASE_PASSWD = this.neteaseProperties.getPassword();


        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.submit(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    ActionEntity entity = null;
                    synchronized (actions) {
                        if (actions == null || actions.size() <= 0)
                            try {
                                actions.wait();
                            } catch (InterruptedException e) {
                            }
                        else
                            entity = actions.remove(0);
                    }
                    runAction(entity);
                }
            }
        });
    }
    public void message(Map<String, String> message ){
        System.out.println(neteaseProperties.getUrl());
        ActionEntity entity = new ActionEntity(message, Action.message);
        HttpRes res = entity.action();
        System.out.println(res.getEntityMap() + "****************");


    }

    public void dynamicMessage(StringBuffer buffer) {
        Map<String, String> message = new HashMap<>();
        message.put("fromAccid", "");
        message.put("toAccids", buffer.toString());
        message.put("type", "1");

    }
    private static void runAction(final ActionEntity entity) {
        if (entity != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        entity.action();
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
    }
    private static HttpRes doaction(String uri, ActionEntity entity) {
        System.out.println("url==============1111==============" + uri);
        HttpPost httpPost = client(uri);
        try {
            HttpClientUtil httpClientUtil = new HttpClientUtil();
            HttpRes res = httpClientUtil.doPost(httpPost, uri, new UrlEncodedFormEntity(entity.createParmas(), "UTF-8"), "UTF-8");
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    private static HttpPost client(String uri) {
        HttpPost httpPost = new HttpPost(uri);

        String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(NETEASE_APPSEC, NETEASE_NONCE ,curTime);

        System.out.println("NETEASE_APPKEY=" + NETEASE_APPKEY + "     !!!!!!!!!!!!!!!!!!!!!!!!!    NETEASE_NONCE=" + NETEASE_NONCE + "");

        httpPost.addHeader("AppKey", NETEASE_APPKEY);
        httpPost.addHeader("Nonce", NETEASE_NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return httpPost;
    }


    private static enum Action {
        message("msg/sendMsg.action"),
        penetrate("msg/sendAttachMsg.action"),
        penetrates("msg/sendMsg.action"),
        batchSending("msg/sendBatchMsg.action"),

        newteam("team/create.action"),
        changeOwner("team/changeOwner.action"),
        addmember("team/add.action"),
        delmember("team/kick.action"),
        disteam("team/remove.action"),
        joinTeams("team/joinTeams.action"),//获取某用户所加入的群信息
        leave("team/leave.action"),//主动退群
        query("team/query.action"),//群信息与成员列表查询

        newuser("user/create.action"),
        edituser("user/updateUinfo.action"),
        getUinfos("user/getUinfos.action");

        private String url;
        Action(String url) {
            this.url = url;
        }

        private HttpRes action(ActionEntity entity) {
            String uri = NETEASE_URL + url;
            return doaction(uri, entity);
        }
    }

    private static class ActionEntity {
        private Map<String, String> entity;
        private Action action;

        public ActionEntity(Map<String, String> entity, Action action) {
            super();
            this.entity = entity;
            this.action = action;
        }

        private List<NameValuePair> createParmas() {
            List<NameValuePair> list = new ArrayList<>();
            if (entity != null && entity.size() > 0)
                for (String key : entity.keySet())
                    list.add(new BasicNameValuePair(key, entity.get(key)));
            return list;
        }

        private HttpRes action() {
            return action.action(this);
        }

        @Override
        public String toString() {
            return action + " - " + entity;
        }
    }



}
