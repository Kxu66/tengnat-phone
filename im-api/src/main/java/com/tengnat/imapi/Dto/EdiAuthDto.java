package com.tengnat.imapi.Dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdiAuthDto implements Serializable {
    private static final long serialVersionUID = -2665767228424279495L;
    private String accessToken;
    private String installationId;
    private String clientId;
}
