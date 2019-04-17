package com.tengnat.mybatis.entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "com_odbc")
public class ComOdbc implements Serializable {


    private static final long serialVersionUID = -5348859102916646315L;
    @Id
    private Integer dbcid;

    private String dbcname;

    private Integer status;

    private String dbcurl;

    private String dbcserver;

    private String dbcuser;

    private String dbcpwd;

    private String remark;

    private String createuser;

    private Date createdate;

    private String updateuser;

    private Date updatedate;

    private String companycode;

    private Integer odbctype;

    private Integer empty;

    private String imeveryonegroupid;

    private Integer isusing;

    private String companyabb;

    private String companylogo;
}
