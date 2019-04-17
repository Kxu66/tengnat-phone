package com.tengnat.elasticsearch.from;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FirstOrderIMFrom implements Serializable {
    private static final long serialVersionUID = 3188986548656881453L;
    private String fromAccount;
    private String to;
    private OrderFrom orderFrom;
}
