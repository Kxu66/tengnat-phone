package com.tengnat.elasticsearch.from;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMsgFrom implements Serializable {

    private static final long serialVersionUID = 8711475691825639886L;
    private Long id;
    private String eventType;
    private String convType;
    private String to;
    private String fromAccount;
    private String fromClientType;
    private String fromDeviceId;
    private String fromNick;
    private String msgTimestamp;
    private String msgType;
    private String body;
    private String attach;
    private String msgidClient;
    private String msgidServer;
    private String resendFlag;
    private String customSafeFlag;
    private String customApnsText;
    private String tMembers;
    private String ext;
    private String antispam;
    private String yidunRes;
    private String blacklist;
    private String orderId1;
    private String orderId2;
    private String ediOrderId;


}
