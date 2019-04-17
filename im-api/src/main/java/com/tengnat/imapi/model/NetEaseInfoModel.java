package com.tengnat.imapi.model;

import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetEaseInfoModel implements Serializable {

    private static final long serialVersionUID = 7928909631701897087L;

    private String userId;

    private Integer dbcId;

    private String dbCode;

    private String netEaseId;

}
