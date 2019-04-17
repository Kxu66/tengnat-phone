package com.tengnat.elasticsearch.controller;


import com.alibaba.fastjson.JSONObject;
import com.tengnat.elasticsearch.from.FirstOrderIMFrom;
import com.tengnat.elasticsearch.service.OrderService;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public IndexResponse addOrder(@RequestBody FirstOrderIMFrom orderIMFrom) {
        System.out.println("********" + orderIMFrom.toString());
        this.orderService.addOrder(orderIMFrom);
        return null;
    }
    @GetMapping("/findByToOrFromAccount")
    public JSONObject  findByToOrFromAccount(String fromAccount,String  to) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 10000);
        List list = this.orderService.findByToOrFromAccount(fromAccount,to);
        jsonObject.put("data", list);
        return jsonObject;
    }
    @GetMapping("/findClientIdByToOrFromAccount")
    public JSONObject  findClientIdByToOrFromAccount(String fromAccount,String  to) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 10000);
        String client = this.orderService.findClientIdByToOrFromAccount(fromAccount, to);
        jsonObject.put("data", client);
        return jsonObject;
    }
    @GetMapping("/findEDIClientId")
    public JSONObject  findEDIClientId() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 10000);
        String client = this.orderService.findEDIClientId();
        jsonObject.put("data", client);
        return jsonObject;
    }
}
