package com.tengnat.imapi.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.tengnat.assistant.data.third.netease.Netease;
import com.tengnat.assistant.exception.BusinessException;
import com.tengnat.imapi.from.ComPushmessgeFrom;
import com.tengnat.imapi.from.FirstOrderIMFrom;
import com.tengnat.imapi.from.OrderDataFrom;
import com.tengnat.imapi.from.OrderFrom;
import com.tengnat.imapi.service.ImPushmentService;
import com.tengnat.mybatis.entity.ComPushment;
import com.tengnat.mybatis.entity.ComPushmessge;
import com.tengnat.mybatis.entity.EdiOrderFirstMessage;
import com.tengnat.mybatis.mapper.ComOdbcMapper;
import com.tengnat.mybatis.mapper.ComPushmentMapper;
import com.tengnat.mybatis.mapper.ComPushmessgeMapper;
import com.tengnat.mybatis.mapper.EdiOrderFirstMessageMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ImPushmentServiceImpl implements ImPushmentService {
    @Autowired
    private Netease netease;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ComPushmessgeMapper comPushmessgeMapper;
    @Autowired
    private ComPushmentMapper comPushmentMapper;
    @Autowired
    private EdiOrderFirstMessageMapper ediOrderFirstMessageMapper;


    //发送订单第一次消息
    @Override
    public void ediFirstMessage(OrderFrom orderFrom) {

        System.out.println(JSONObject.toJSON(orderFrom));
        Map<String, String> map = new HashMap<>();
        //获取发送者云信id
        String from = this.getImAccid(orderFrom.getFromOpenid(),orderFrom.getClientId()).getString("netEaseId");
        if(StringUtils.isEmpty(from)){
            throw new BusinessException("发送者云信账号为空");
        }
        //获取接收者云信id
        String to = this.getImAccid(orderFrom.getToOpenid(),orderFrom.getClientId()).getString("netEaseId");
        if(StringUtils.isEmpty(to)){
            throw new BusinessException("接收者云信账号为空");
        }

        JSONObject customJson = new JSONObject();
        //自定义消息类型 14
        customJson.put("type", 14);
        customJson.put("data", JSONObject.toJSON(orderFrom));
        JSONObject ediOrderJson = new JSONObject();
        List<Long> orderList = new ArrayList<>();
        orderList.add(orderFrom.getData().getSourceOrderId());
        orderList.add(orderFrom.getData().getTargetOrderId());
        ediOrderJson.put("edi_orderid", orderList);
        map.put("from", from);
        map.put("to", to);
        map.put("ope", "0");
        //自定义消息 100
        map.put("type", "100");
        map.put("body",customJson.toJSONString());
        map.put("ext",ediOrderJson.toJSONString());
        //发送云信消息
        netease.message(map);
        FirstOrderIMFrom orderIMFrom = new FirstOrderIMFrom();
        orderIMFrom.setFromAccount(from);
        orderIMFrom.setTo(to);
        orderIMFrom.setOrderFrom(orderFrom);
        //向请求保存ES订单第一次消息
        OrderDataFrom data = orderFrom.getData();
        int count = this.ediOrderFirstMessageMapper.selectCountBySourceOrderIdAndTargetOrderId(data.getSourceOrderId(), data.getTargetOrderId());
        if(count==0){
            Date date = new Date();
            EdiOrderFirstMessage ediOrderFirstMessage = EdiOrderFirstMessage.builder().orderType(data.getOrderType()).sourceOrderId(data.getSourceOrderId())
                    .sourceOrderNo(data.getSourceOrderNo()).sourceOrderUrl(data.getSourceOrderUrl()).targetOrderId(data.getTargetOrderId()).targetOrderNo(data.getTargetOrderNo())
                    .targetOrderUrl(data.getTargetOrderUrl()).buyerExpectedReceivedDatetime(data.getBuyerExpectedReceivedDatetime())
                    .materielImg(data.getMaterielImg()).materiels(data.getMateriels())
                    .clientId(orderFrom.getClientId())
                    .imFromAccount(from).fromOpenid(orderFrom.getFromOpenid()).sourceAddress(data.getSourceAddress()).sourceCardName(data.getSourceCardName())
                    .targetAddress(data.getTargetAddress()).targetCardName(data.getTargetCardName()).imTo(to).toOpenid(orderFrom.getToOpenid())
                    .totalMaterielAmount(data.getTotalMaterielAmount()).totalPrice(data.getTotalPrice()).createTime(date).build();
            this.ediOrderFirstMessageMapper.insertSelective(ediOrderFirstMessage);
        }else {
            System.out.println("**************"+"该结构化消息已存在");
        }
        this.addEdiFirstMessage(orderIMFrom);

    }


    //获取Open消息  {"code":10000,"message":"成功","data":{"netEaseId":"guest000199_yyta0000001","userId":"guest000199","dbcId":2,"dbcCode":"YYTa0000001"}}
    private JSONObject getImAccid(String openid, String clientId){
        String url = "https://open.yiyuntong.com/authorize/net-ease-info?";
        url += "client_id=" + clientId;
        url += "&openid=" + openid;
        String rgs = restTemplate.getForEntity(url,String.class).getBody();
        JSONObject jsonObject = JSONObject.parseObject(rgs);
        int code = jsonObject.getIntValue("code");
        if (code == 10000){
            return  jsonObject.getJSONObject("data");
        }
        return new JSONObject();
    }
    //向请求保存ES订单第一次消息
    private void addEdiFirstMessage(FirstOrderIMFrom orderIMFrom){
        String url = "http://www.yiyuntong.com/esdata/order/addOrder";
//        String url = "http://192.168.5.18:8080/order/addOrder";
        restTemplate.postForEntity(url,JSONObject.toJSON(orderIMFrom),String.class).getBody();
    }
    //云秘书推送
    @Override
    public ComPushmessge sendMessage(ComPushmessgeFrom comPushmessgeFrom) {
        ComPushment comPushment = this.comPushmentMapper.selectByPrimaryKey(comPushmessgeFrom.getPushmentid());
        JSONObject fromJsonNet = this.getImAccid(comPushmessgeFrom.getFromOpenid(), comPushment.getClientid());
        //发送者
        String fromUserId = fromJsonNet.getString("userId");
        JSONObject toJsonNet = this.getImAccid(comPushmessgeFrom.getToOpenid(), comPushment.getClientid());
        //发送者
        String to = toJsonNet.getString("netEaseId");
        String toUserId = toJsonNet.getString("userId");
        Integer toDbcId = toJsonNet.getInteger("dbcId");
        String toDbcCode = toJsonNet.getString("dbcCode");

        if(StringUtils.isNotBlank(to)){
            Map<String, String> map = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            String message = comPushmessgeFrom.getPushcontent();
            jsonObject.put("msg", message);

            String from = "message_" + toDbcCode + "@form";
            map.put("from", from.toLowerCase());
            map.put("to", to);
            map.put("ope", "0");
            map.put("type", "0");
            map.put("body",jsonObject.toJSONString());
            //发送云信消息
            this.netease.message(map);
            ComPushmessge comPushmessge = ComPushmessge.builder().contentkey(comPushmessgeFrom.getContentkey()).dbcid(toDbcId)
                    .imageurl(comPushmessgeFrom.getImageurl()).pushcontent(comPushmessgeFrom.getPushcontent()).pushdatetime(new Date())
                    .pushjson(comPushmessgeFrom.getPushjson()).pushmentid(comPushmessgeFrom.getPushmentid()).pushstatus(0)
                    .pushtitle(comPushmessgeFrom.getPushtitle()).pushuser(fromUserId).receiver(toUserId).relativeurl(comPushmessgeFrom.getRelativeurl()).build();
            this.comPushmessgeMapper.insertSelective(comPushmessge);
            return comPushmessge;
        }

        return null;

    }

    @Override
    public List findByToOrFromAccount(String fromAccount, String to) {
        return  this.ediOrderFirstMessageMapper.selectByToOrFromAccount(fromAccount,to);
    }
}
