package com.tengnat.imapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.tengnat.assistant.config.DataSourceHolder;
import com.tengnat.imapi.Dto.EdiAuthDto;
import com.tengnat.imapi.from.ComPushmessgeFrom;
import com.tengnat.imapi.from.OrderFrom;
import com.tengnat.imapi.service.ImPushmentService;
import com.tengnat.mybatis.entity.ComPushmessge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone/push")
public class ImPushmentController {
    @Autowired
    private ImPushmentService imPushmentService;
    //edi订单第一次时发送自定义消息
    @PostMapping("edi/first/message")
    public JSONObject ediFirstMessage(@RequestBody OrderFrom orderFrom){
        JSONObject jsonObject = new JSONObject();
        this.imPushmentService.ediFirstMessage(orderFrom);
        jsonObject.put("code", 10000);
        jsonObject.put("data","");
        jsonObject.put("message","");
        return jsonObject;
    }
    @PostMapping("send/message")
    public JSONObject sendMessage(@RequestBody ComPushmessgeFrom comPushmessgeFrom){
        JSONObject jsonObject = new JSONObject();
        DataSourceHolder.setDataSourceType("0");
        ComPushmessge comPushmessge = this.imPushmentService.sendMessage(comPushmessgeFrom);
        System.out.println("ddd");
        jsonObject.put("code", 10000);
        jsonObject.put("data",comPushmessge);
        jsonObject.put("message","");
        return jsonObject;
    }


}
