package com.tengnat.imapi.from;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticePushmessageFrom implements Serializable {

    private static final long serialVersionUID = -4652680331981352552L;

    @NotNull(message = "发送人openId不能空")
    private String fromOpenid;
    @NotNull(message = "接收人openId不能空")
    private String toOpenid;
    @NotNull(message = "clientId不能空")
    private String clientId;
    @NotNull(message = "data不能空")
    private NoticePushmessageDataFrom data;
}
