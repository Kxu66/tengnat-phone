package com.tengnat.imapi.service;

import com.tengnat.imapi.from.ComPushmessgeFrom;
import com.tengnat.imapi.from.OrderDataFrom;
import com.tengnat.imapi.from.OrderFrom;
import com.tengnat.mybatis.entity.ComPushmessge;

public interface ImPushmentService  {

    void ediFirstMessage(OrderFrom orderFrom);

    ComPushmessge sendMessage(ComPushmessgeFrom comPushmessgeFrom);
}
