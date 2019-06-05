package com.tengnat.imapi.service;

import com.tengnat.imapi.from.*;
import com.tengnat.mybatis.entity.ComPushmessge;

import java.util.List;

public interface ImPushmentService  {

    void ediFirstMessage(OrderFrom orderFrom);

    ComPushmessge sendMessage(ComPushmessgeFrom comPushmessgeFrom);

    List findByToOrFromAccount(String fromAccount, String to);

    void sendMessagePurchase(NoticePushmessageFrom noticePushmessageFrom);

    void sendMessageMarket(NoticePushmessageFrom noticePushmessageFrom);

    void updateSaleOrPurchaseOrderStatus(EdiOrderFirstFrom orderFirstFrom);
}
