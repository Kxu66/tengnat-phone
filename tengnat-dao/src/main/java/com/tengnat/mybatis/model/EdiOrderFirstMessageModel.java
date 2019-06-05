package com.tengnat.mybatis.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdiOrderFirstMessageModel implements Serializable {
    private static final long serialVersionUID = -5520451055686613741L;
    private int fmId;
    private String imFromAccount;
    private String imTo;
    private String fromOpenid;
    private String toOpenid;
    private String clientId;
    private Long sourceOrderId;
    private Long targetOrderId;
    private Long buyerExpectedReceivedDatetime;
    private String orderType;
    private String sourceCardName;
    private String targetCardName;
    private String targetAddress;
    private String sourceAddress;
    private String materiels;
    private Double totalMaterielAmount;
    private Double totalPrice;
    private String sourceOrderNo;
    private String targetOrderNo;
    private String sourceOrderUrl;
    private String targetOrderUrl;
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
