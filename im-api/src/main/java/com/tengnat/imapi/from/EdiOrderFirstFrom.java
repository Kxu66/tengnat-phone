package com.tengnat.imapi.from;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EdiOrderFirstFrom implements Serializable {
    private static final long serialVersionUID = 4866958985471125821L;
    /**
     * 采购方的openId
     */
    @NotNull(message = "发送人openId不能空")
    private String fromOpenid;
    /**
     * 销售方的openId
     */
    @NotNull(message = "接收人openId不能空")
    private String toOpenid;
    /**
     * 一云通分配的clientId
     */
    @NotNull(message = "clientId不能空")
    private String clientId;
    /**
     *  销售订单主状态
     */
    private Integer saleOrderStatus;
    /**
     *
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
    /**
     * 采购方期望收货日期
     */
    private Long buyerExpectedReceivedDatetime;
    /**
     * 销售订单id
     */
    private Long salesOrderId;
    /**
     * 采购订单id
     */
    private Long purchaseOrderId;


}
