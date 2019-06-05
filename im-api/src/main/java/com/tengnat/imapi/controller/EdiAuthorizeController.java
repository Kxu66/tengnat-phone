package com.tengnat.imapi.controller;


import com.tengnat.imapi.Dto.ReturnData;
import com.tengnat.imapi.service.EdiAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone/edi")
public class EdiAuthorizeController {

    @Autowired
    private EdiAuthorizeService ediAuthorizeService;

    @GetMapping("authorize")
    public ReturnData ediAuthorize(@RequestParam("openId") String openId,
                                   @RequestParam("clientId")String clientId,
                                   @RequestParam("targetClientId")String targetClientId){
//        DataSourceHolder.setDataSourceType(153);

        ReturnData returnData = this.ediAuthorizeService.ediAuthorize(openId,clientId,targetClientId);

        return returnData;
    }

}
