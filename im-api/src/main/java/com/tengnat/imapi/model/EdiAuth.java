package com.tengnat.imapi.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdiAuth implements Serializable {
    private static final long serialVersionUID = 1535354613816167678L;
    private Integer code;
    private String alert;
    private Date time;
    private String version;
    private EdiAuthData data;
}
