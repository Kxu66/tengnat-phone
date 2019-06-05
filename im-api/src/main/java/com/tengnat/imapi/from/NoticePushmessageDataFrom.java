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
public class NoticePushmessageDataFrom implements Serializable {

    private static final long serialVersionUID = 6244886235157969392L;
    /**
     * 1：销售方修改订单日期；2：销售方确认订单；
     * 3：销售方取消订单 4：销售方部分发货 5：销售方全部发货
     * 6：采购方修改到货日期；7：采购方修改仓库
     * 8：采购方确认订单；9：采购方取消订单
     * 10：采购方收货；11：采购方同时修改到货日期和仓库
     */
    @NotNull(message = "不为空")
    private Integer noticeType;
    /**
     * 销售单id
     */
    @NotNull(message = "不为空")
    private Long saleOrderId;
    /**
     * 销售单编号
     */
    @NotNull(message = "不为空")
    private String saleOrderNo;
    /**
     * 销售单URL
     */
    private String saleOrderUrl;
    /**
     * 采购单id
     */
    @NotNull(message = "不为空")
    private Long purchaseOrderId;
    /**
     * 采购单编号
     */
    @NotNull(message = "不为空")
    private String purchaseOrderNo;
    /**
     * 采购单URL
     */
    private String purchaseOrderUrl;
    /**
     * 发货单订单id
     * noticeType为10,11时必须传递
     */
    private Long deliveryOrderId;
    /**
     * 发货单订单编号
     * noticeType为10,11时必须传递
     */
    private String deliveryOrderNo;
    /**
     * 发货单订单编号
     * noticeType为10,11时必须传递
     */
    private String deliveryOrderUrl;
    /**
     * 收货订单Id
     * noticeType为10,11时必须传递
     */
    private Long receiveOrderId;
    /**
     * 收货订单单号
     * noticeType为10,11时必须传递
     */
    private String receiveOrderNo;
    /**
     * 收货订单Url
     * noticeType为10,11时必须传递
     */
    private String receiveOrderUrl;
    /**
     * 联系人姓名
     * noticeType为1,2,3，6,7,8,9时，必须传递
     */
    private String contactName;
    /**
     * 订单修改之前的到货日期
     * noticeType为1，6时必须传递
     */
    private Date originalTime;
    /**
     * 订单修改之后的到货日期
     * noticeType为1，6时必须传递
     */
    private Date modifiedTime;

    /**
     * 订单修改之前的仓库
     * noticeType为7时必须传递
     */
    private String originalWarehouse;
    /**
     * 订单修改之后的仓库
     * noticeType为7时必须传递
     */
    private String modifiedWarehouse;


}
