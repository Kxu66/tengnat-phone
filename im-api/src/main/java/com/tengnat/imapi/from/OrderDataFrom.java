package com.tengnat.imapi.from;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDataFrom implements Serializable {
    private static final long serialVersionUID = -3536524167622001874L;
    /**
     * 采购订单
     */
    @NotNull(message = "不为空")
    private Long sourceOrderId;
    /**
     * 销售订单
     */
    @NotNull(message = "不为空")
    private Long targetOrderId;
    private Long buyerExpectedReceivedDatetime;
    private String orderType;
    /**
     * 采购名字
     */
    private String sourceCardName;
    /**
     *销售名字
     */
    private String targetCardName;
    /**
     * 收获地址
     */
    private String sourceAddress;
    /**
     * 交货地址
     */
    private String targetAddress;
    private String materiels;
    private Double totalMaterielAmount;
    private Double totalPrice;
    private String sourceOrderNo;
    private String targetOrderNo;
    private String sourceOrderUrl;
    private String targetOrderUrl;
    private String materielImg;
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
