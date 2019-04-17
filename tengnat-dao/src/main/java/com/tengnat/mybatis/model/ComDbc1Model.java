package com.tengnat.mybatis.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComDbc1Model implements Serializable {
    private static final long serialVersionUID = 3116666771236588092L;
    private Long id;

    private Integer dbcid;

    private String userid;

    private Integer easemob;

    private String neteaseid;

    private Integer wdauth;

    private Integer crmauth;

    private Integer mailauth;

    private Integer erpauth;

    private Integer cloudauth;

    private Integer mesauth;

    private Integer shopauth;

    private Integer barcodeauth;
}
