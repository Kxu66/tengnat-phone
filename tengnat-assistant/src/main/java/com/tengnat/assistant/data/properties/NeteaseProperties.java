package com.tengnat.assistant.data.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "netease")
public class NeteaseProperties {

    private  String appkey;

    private  String appsec;

    private  String url;

    private  String nonce;

    private String password;
}
