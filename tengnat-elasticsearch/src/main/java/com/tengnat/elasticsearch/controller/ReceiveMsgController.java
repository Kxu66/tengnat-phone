package com.tengnat.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tengnat.elasticsearch.from.ReceiveMsgFrom;
import com.tengnat.elasticsearch.service.ReceiveMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/receive")
public class ReceiveMsgController {
    @Autowired
    private ReceiveMsgService receiveMsgService;

    /**
     * 测试
     * 添加索引
     */
    @PostMapping("/addIndex")
    public String addIndex(String title, String content) throws IOException {
        receiveMsgService.addIndex(title, content);
        return "ok";
    }

    @PostMapping("/addReceive")
    public JSONObject addReceive(@RequestBody ReceiveMsgFrom receiveMsg) {
        System.out.println("********" + receiveMsg.toString());
        if ("1".equals(receiveMsg.getEventType()) && "PERSON".equals(receiveMsg.getConvType()) && !StringUtils.isEmpty(receiveMsg.getExt())) {
            Date date = new Date();
            receiveMsg.setId(date.getTime());
            JSONObject jsonObject = JSON.parseObject(receiveMsg.getExt());
            JSONArray ediOrderid = jsonObject.getJSONArray("edi_orderid");
            if (ediOrderid != null && ediOrderid.size() == 2) {
                receiveMsg.setEdiOrderId(ediOrderid.toString());
                receiveMsg.setOrderId1(ediOrderid.getString(0));
                receiveMsg.setOrderId2(ediOrderid.getString(1));
                receiveMsgService.addReceive(receiveMsg);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 10000);
        jsonObject.put("data", "");
        return jsonObject;
    }

    @GetMapping("/findByEdiOrderIdOrToOrFromAccount")
    public JSONObject findByEdiOrderIdOrToOrFromAccount(@RequestParam("orderId1") String orderId1, @RequestParam("orderId2") String orderId2,
                                                        @RequestParam(value = "msgTimestamp", required = false) Long msgTimestamp,
                                                        @RequestParam(value = "msgType", required = false) String msgType,
                                                        @RequestParam("size") int size) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 10000);
        List<Map<String, Object>> list = receiveMsgService.findByEdiOrderIdOrToOrFromAccount(orderId1,orderId2,msgTimestamp,msgType,size);
        jsonObject.put("data", list);
        return jsonObject;

    }

}