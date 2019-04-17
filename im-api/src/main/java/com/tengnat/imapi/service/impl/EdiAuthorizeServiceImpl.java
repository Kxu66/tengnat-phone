package com.tengnat.imapi.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.tengnat.imapi.Dto.EdiAuthDto;
import com.tengnat.imapi.Dto.ReturnData;
import com.tengnat.imapi.model.EdiAuth;
import com.tengnat.imapi.service.EdiAuthorizeService;
import com.tengnat.imapi.utils.SignUtil;
import com.tengnat.imapi.utils.data.resources.EdiAuthorizeResources;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EdiAuthorizeServiceImpl implements EdiAuthorizeService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EdiAuthorizeResources ediAuthorizeResources;


    @Override
    public ReturnData ediAuthorize(String openId, String clientId, String targetClientId) {
        String param = "openid=" + openId + "&client_id=" + clientId + "&target_client_id=" + targetClientId;
        ReturnData returnData = restTemplate.getForEntity("https://open.yiyuntong.com/authorize/trans-obtain-code?"+param, ReturnData.class).getBody();

//        JSONObject jsonObject = JSONObject.parseObject(rgs);
//        int code = jsonObject.getIntValue("code");
        if (returnData != null && returnData.getCode() == 10000){
            String authorizationCode  = returnData.getData().toString();
            String url = this.ediAuthorizeResources.getUrl();
            url += "auth_type=" + this.ediAuthorizeResources.getAuthType();
            url += "&auth_code=" + authorizationCode;
            url += "&app_id=" + this.ediAuthorizeResources.getAppId();
            url += "&timestamp=" + System.currentTimeMillis();
            String sign = SignUtil.sign(url, null, this.ediAuthorizeResources.getSecretKey());
            url += "&sign=" + sign;
            EdiAuth ediAuth = restTemplate.getForObject(url, EdiAuth.class);
            if (ediAuth != null && ediAuth.getCode() == 100000) {
                EdiAuthDto build = EdiAuthDto.builder().accessToken(ediAuth.getData().getAccessToken()).clientId(ediAuth.getData().getClientId())
                        .installationId(ediAuth.getData().getInstallationId()).build();

                 returnData.setData(build);
            }else {
                returnData.setData(ediAuth);
            }
        }
        return returnData;
    }
}
