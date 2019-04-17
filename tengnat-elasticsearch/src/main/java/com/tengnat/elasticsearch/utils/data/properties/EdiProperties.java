package com.tengnat.elasticsearch.utils.data.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@Component
@ConfigurationProperties("edi")
public class EdiProperties implements Serializable {
    private static final long serialVersionUID = -5561194511055575478L;

    private String clientId;

}
