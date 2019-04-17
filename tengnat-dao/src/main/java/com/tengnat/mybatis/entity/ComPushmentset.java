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
@Table(name = "com_pushmentset")
public class ComPushmentset implements Serializable {
    private static final long serialVersionUID = -6283014810128711645L;
    /**
     * 开关id
     */
    @Id
    private Integer setid;
    /**
     * 公司id
     */
    private Integer dbcid;
    /**
     * 推送模板id
     */
    private Integer pushmentid;
    /**
     * 是否开启 1是 0否
     */
    private Integer pushenable;
}
