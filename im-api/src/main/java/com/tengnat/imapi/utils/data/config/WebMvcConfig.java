package com.tengnat.imapi.utils.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengnat.imapi.handler.DataContainer;
import com.tengnat.imapi.interceptor.AccessInterceptor;
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
        registry.addInterceptor(accessInterceptor).order(9000).excludePathPatterns("/phone/edi/authorize",
                "/phone/push/send/message",
                "/phone/push/edi/first/message",
                "/phone/push/findByToOrFromAccount",
                "/phone/push/send/message/purchase",
                "/phone/push/send/message/market",
                "/phone/push/update/order/status"
                );

    }
}
