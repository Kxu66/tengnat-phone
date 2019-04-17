package com.tengnat.imapi.Dto;

import com.alibaba.fastjson.JSONObject;
import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnData implements Serializable {
    private static final long serialVersionUID = -2825722697653694086L;

    private int code;

    private String message;

    private Object data;

}
