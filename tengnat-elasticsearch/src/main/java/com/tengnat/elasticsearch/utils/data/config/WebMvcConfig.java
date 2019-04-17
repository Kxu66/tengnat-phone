package com.tengnat.elasticsearch.utils.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengnat.elasticsearch.handler.DataContainer;
import com.tengnat.elasticsearch.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private DataContainer dataContainer;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AccessInterceptor accessInterceptor = new AccessInterceptor();
        accessInterceptor.setDataContainer(dataContainer);
        accessInterceptor.setObjectMapper(objectMapper);
        registry.addInterceptor(accessInterceptor).order(9000).excludePathPatterns("/order/**");

    }
}
