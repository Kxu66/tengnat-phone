package com.tengnat.elasticsearch.service;

import com.tengnat.elasticsearch.from.ReceiveMsgFrom;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReceiveMsgService {
     void addIndex(String title, String content) throws IOException;

    void addReceive(ReceiveMsgFrom receiveMsg);

    List<Map<String, Object>> findByEdiOrderIdOrToOrFromAccount(String orderId1, String orderId2, Long msgTimestamp, String msgType, int size);



}
