package com.tengnat.assistant.data.properties;



import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("default-db")
public class DataBaseProperties {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private DataSourceProperties hikari;

    @Getter
    @Setter
    public static class DataSourceProperties {

        private Integer minimumIdle;

        private Integer maximumPoolSize;
    }

}

