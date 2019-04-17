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
@Table(name = "com_pushment")
public class ComPushment implements Serializable {
    private static final long serialVersionUID = 2095100563267542851L;
    /**
     * 推送模板id
     */
    @Id
    private Integer pushmentid;
    /**
     * 推送模板名称
     */
    private String pushname;
    /**
     * 列表样式
     */
    private String pushstyle;
    /**
     * 跳转类型 1、原生 2、网页 3、第三方app
     */
    private Integer guidetype;
    /**
     * 安卓原生详情页面路径
     */
    private String andpath;
    /**
     * ios原生详情页面路径
     */
    private String iospath;
    /**
     * 网页详情路径
     */
    private String weburl;
    /**
     * 描述
     */
    private String remark;
    /**
     * 归属公司id
     */
    private Integer gsdbcid;
    /**
     * 头像
     */
    private String icon;
    /**
     *  第三方APP的路径
     */
    private String appurl;
    /**
     * 样式1  默认图片
     */
    private String  preinstalled;
    /**
     * 该推送消息所用平台clientId
     */
    private String  clientid;
}
