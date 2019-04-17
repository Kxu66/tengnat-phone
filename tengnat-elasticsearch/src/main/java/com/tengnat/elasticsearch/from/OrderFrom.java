package com.tengnat.elasticsearch.from;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderFrom implements Serializable {
    private static final long serialVersionUID = 7592815447474523343L;
    private String fromOpenid;
    private String toOpenid;
    private String clientId;
    private OrderDataFrom data;
}
