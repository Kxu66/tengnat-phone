package com.tengnat.imapi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengnat.assistant.data.model.LoginModel;
import com.tengnat.assistant.data.resp.InvokeResult;
import com.tengnat.imapi.handler.DataContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AccessInterceptor implements HandlerInterceptor {
    @Autowired
    private DataContainer dataContainer;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String loginToken = request.getHeader("yyt-im-token");
        if(StringUtils.isBlank(loginToken)){
            response.getWriter().write(this.objectMapper.writeValueAsString(new InvokeResult(InvokeResult.FAIL_CODE,"缺少访问令牌")));
            return false;
        }
        LoginModel loginModel = this.dataContainer.getLoginModel(loginToken);
        if (loginModel != null) {
            return true;
        }
        response.getWriter().write(this.objectMapper.writeValueAsString(InvokeResult.noLogin()));

        return false;
    }

    public void setDataContainer(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
