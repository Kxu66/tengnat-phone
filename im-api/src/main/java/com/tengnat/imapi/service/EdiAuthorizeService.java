package com.tengnat.imapi.service;


import com.tengnat.imapi.Dto.EdiAuthDto;
import com.tengnat.imapi.Dto.ReturnData;

public interface EdiAuthorizeService {
    ReturnData ediAuthorize(String openId, String clientId, String targetClientId);
}
