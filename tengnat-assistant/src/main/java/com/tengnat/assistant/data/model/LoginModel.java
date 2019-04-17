package com.tengnat.assistant.data.model;

import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel implements Serializable {

    private static final long serialVersionUID = 7370899429383222566L;
    /**
     * 公司名全称
     */
    private String companyallname;

    /**
     * 公司名简称
     */
    private String companyname;

    /**
     * SAP ID
     */
    private String sapid;

    /**
     * 数据库ID
     */
    private String dbcid;

    /**
     * 数据库名称
     */
    private String dbname;

    /**
     * 用户ID
     */
    private String userid;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String usermobile;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 部门ID
     */
    private String userorgid;

    /**
     * 部门名称
     */
    private String userorgname;

    /**
     * 申请人数
     */
    private Integer applypeople;

    /**
     * 用户头像
     */
    private String userphoto;

    /**
     * 不知道是啥
     */
    private String usertoken;

    /**
     * 用户访问的凭证
     */
    private String loginToken;
}
