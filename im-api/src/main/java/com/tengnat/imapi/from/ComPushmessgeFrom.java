package com.tengnat.imapi.from;


import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComPushmessgeFrom implements Serializable {
    private static final long serialVersionUID = -6078622739209068097L;
    /**
     * 推送记录id
     */
    private Integer messageid;
    /**
     * 推送模板id
     */
    private Integer pushmentid;

    /**
     * 推送内容
     */
    private String pushcontent;
    /**
     * 推送时间
     */
    private Date pushdatetime;
    /**
     * 推送json
     */
    private String pushjson;
    /**
     * 推送公司
     */
    private Integer dbcid;
    /**
     * 推送人
     */
    private String pushuser;
    /**
     * 接收人
     */
    private String receiver;
    /**
     * 1已读   0未读
     */
    private Integer pushstatus;
    /**
     * 推送标题
     */
    private String pushtitle;
    /**
     * 推送内容的关联主键
     */
    private Integer contentkey;
    /**
     * 相对路径
     */
    private String relativeurl;
    /**
     * 推送图片RUL（绝对路径）
     */
    private String imageurl;
    /**
     * 外部推送 发送 openId
     */
    private String fromOpenid;
    /**
     * 外部推送 接收 openId
     */
    private String toOpenid;
}
