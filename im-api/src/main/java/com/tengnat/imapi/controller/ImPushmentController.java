package com.tengnat.imapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.tengnat.assistant.config.DataSourceHolder;
import com.tengnat.imapi.Dto.EdiAuthDto;
import com.tengnat.imapi.from.*;
import com.tengnat.imapi.service.ImPushmentService;
import com.tengnat.mybatis.entity.ComPushmessge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phone/push")
public class ImPushmentController {
    @Autowired
    private ImPushmentService imPushmentService;
    //edi订单第一次时发送自定义消息
    @PostMapping("edi/first/message")
    public JSONObject ediFirstMessage(@Validated @RequestBody OrderFrom orderFrom){
        JSONObject jsonObject = new JSONObject();
        DataSourceHolder.setDataSourceType("0");
        this.imPushmentService.ediFirstMessage(orderFrom);
        jsonObject.put("code", 10000);
        jsonObject.put("data","");
        jsonObject.put("message","");
        return jsonObject;
    }
    //发送推送消息
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
    @GetMapping("/findByToOrFromAccount")
    public JSONObject  findByToOrFromAccount(String fromAccount,String  to) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 10000);
        List list = this.imPushmentService.findByToOrFromAccount(fromAccount,to);
        jsonObject.put("data", list);
        return jsonObject;
    }

    /**
     * 销售方发一云通消息
     */
    @PostMapping("send/message/market")
    public JSONObject sendMessageMarket(@RequestBody NoticePushmessageFrom noticePushmessageFrom){
        JSONObject jsonObject = new JSONObject();
        DataSourceHolder.setDataSourceType("0");
        System.out.println(noticePushmessageFrom.toString());
        this.imPushmentService.sendMessageMarket(noticePushmessageFrom);
        System.out.println("market");
        jsonObject.put("code", 10000);
        jsonObject.put("data","");
        jsonObject.put("message","成功");
        return jsonObject;
    }

    /**
     *采购方发一云通消息
     */
    @PostMapping("send/message/purchase")
    public JSONObject sendMessagePurchase(@RequestBody NoticePushmessageFrom noticePushmessageFrom){
        JSONObject jsonObject = new JSONObject();
        DataSourceHolder.setDataSourceType("0");
        System.out.println(noticePushmessageFrom.toString());
        this.imPushmentService.sendMessagePurchase(noticePushmessageFrom);
        System.out.println("purchase");
        jsonObject.put("code", 10000);
        jsonObject.put("data","");
        jsonObject.put("message","成功");
        return jsonObject;
    }

    /**
     * 更新销售和采购订单状态
     */
    @PostMapping("update/order/status")
    public JSONObject updateSaleOrPurchaseOrderStatus(@RequestBody EdiOrderFirstFrom orderFirstFrom){
        JSONObject jsonObject = new JSONObject();
        DataSourceHolder.setDataSourceType("0");
        System.out.println(orderFirstFrom.toString());
        this.imPushmentService.updateSaleOrPurchaseOrderStatus(orderFirstFrom);
        jsonObject.put("code", 10000);
        jsonObject.put("data","");
        jsonObject.put("message","成功");
        return jsonObject;
    }
    /**
     * 更新采购订单状态
     */
    @PostMapping("update/purchase/order/status")
    public JSONObject updatePurchaseOrderStatus(){
        JSONObject jsonObject = new JSONObject();


        jsonObject.put("code", 10000);
        jsonObject.put("data","");
        jsonObject.put("message","成功");
        return jsonObject;
    }

}
