package com.tengnat.imapi.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdiAuthData implements Serializable {
    private static final long serialVersionUID = -7467504730960801682L;
    private String accessToken;
    private Date accessTokenExpiredDatetime;
    private String refreshedToken;
    private Date refreshTokenExpiredDatetime;
    private String openId;
    private String installationId;
    private String clientId;
}
