package com.tengnat.mybatis.entity;

import lombok.*;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "edi_order_first_message")
public class EdiOrderFirstMessage implements Serializable {
    private static final long serialVersionUID = 1290238914051690103L;
    private int fmId;
    private String imFromAccount;
    private String imTo;
    private String fromOpenid;
    private String toOpenid;
    private String clientId;
    /**
     * 采购订单
     */
    private Long sourceOrderId;
    /**
     * 销售订单
     */
    private Long targetOrderId;
    /**
     * 要求到货日期
     */
    private Long buyerExpectedReceivedDatetime;
    /**
     * 订单类型（1、采购 2、销售）
     */
    private String orderType;
    /**
     * 供应商/客户名称
     */
    private String sourceCardName;
    /**
     * 供应商/客户名称
     */
    private String targetCardName;
    private String targetAddress;
    private String sourceAddress;
    /**
     * 物料名称
     */
    private String materiels;
    /**
     * 订单共几种物料
     */
    private Double totalMaterielAmount;
    /**
     * 订单总金额
     */
    private Double totalPrice;
    private String sourceOrderNo;
    private String targetOrderNo;
    private String sourceOrderUrl;
    private String targetOrderUrl;
    /**
     * 物料图片（若有则展示在结构化消息中，若无则不显示）
     */
    private String materielImg;
    private Date createTime;
    /**
     *  销售订单主状态
     *  2 --未发送  3 --未确认  4 --未发货  5 --部分发货  6 --全部发货
     *   7 --	部分收货  8 --全部收货  9 --已取消  10 --已完成
     */
    private Integer saleOrderStatus;
    /**
     ** 2 --未发送  3 --未确认  4 --未发货  5 --部分发货  6 --全部发货
     *      * 7 --	部分收货  8 --全部收货  9 --已取消  10 --已完成
     * 采购订单主状态
     */
    private Integer purchaseOrderStatus;
    /**
     * 销售订单子状态
     */
    private Integer salesSubStatus;
    /**
     * 采购订单子状态
     */
    private Integer purchaseSubStatus;
}
