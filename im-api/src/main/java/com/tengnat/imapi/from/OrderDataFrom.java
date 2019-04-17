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
//    @NotNull(message = "不为空")
    private Long sourceOrderId;
//    @NotNull(message = "不为空")
    private Long targetOrderId;
    private Long buyerExpectedReceivedDatetime;
    private String orderType;
    private String cardName;
    private String address;
    private String materiels;
    private Double totalMaterielAmount;
    private Double totalPrice;
    private String sourceOrderNo;
    private String targetOrderNo;
    private String sourceOrderUrl;
    private String targetOrderUrl;
    private String materielImg;


}
