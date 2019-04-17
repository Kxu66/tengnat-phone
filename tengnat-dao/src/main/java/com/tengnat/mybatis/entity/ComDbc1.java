package com.tengnat.mybatis.entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "com_dbc1")
public class ComDbc1 implements Serializable {

    private static final long serialVersionUID = -8074065289487833466L;

    @Id
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
