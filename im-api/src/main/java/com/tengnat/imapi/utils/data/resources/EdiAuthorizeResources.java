package com.tengnat.imapi.utils.data.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("edi-authorize")
public class EdiAuthorizeResources {

    private String url;

    private String authType;

    private String appId;

    private String secretKey;
}
