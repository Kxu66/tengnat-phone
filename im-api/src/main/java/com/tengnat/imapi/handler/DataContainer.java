package com.tengnat.imapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengnat.assistant.data.model.LoginModel;
import com.tengnat.imapi.utils.data.constants.DataConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataContainer {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public LoginModel getLoginModel(String loginToken) {
        try {
            String loginModelString = this.stringRedisTemplate.opsForValue().get(DataConstants.LOGIN_TOKEN + loginToken);
            if (StringUtils.isNotBlank(loginModelString)) {
                return this.objectMapper.readValue(loginModelString, LoginModel.class);
            }
        } catch (Exception e) {
            log.error("获取登录信息出错", e);
        }
        return null;
    }
}
