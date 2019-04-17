package com.tengnat.imapi.controller;

import com.tengnat.assistant.config.DataSourceHolder;
import com.tengnat.imapi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("phone/test")
public class TestController {
    @Autowired
    private TestService testService;
    @RequestMapping("tt")
    public String test(Integer dbcId){
        DataSourceHolder.setDataSourceType("0");
        testService.test(dbcId);
        return null;
    }


}
