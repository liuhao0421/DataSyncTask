package com.liuhao.datasynctask.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhao
 * @since 2023-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("red_envelope")
public class RedEnvelopeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

      @TableId(value = "appid", type = IdType.INPUT)
    private String appid;

    private String openid;

    private String cardid;

    private BigDecimal envelopeAmt;

    private String envelopeType;

    private String envelopeStatus;

    private String envelopeSource;

    private LocalDateTime createdate;

    private LocalDateTime gettime;

    private LocalDateTime envelopeStarttime;

    private LocalDateTime envelopeEndtime;

    private BigDecimal orderAmt;

    private String awarduuid;

    private String remark;

    private BigDecimal awardId;

      @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedate;

    private LocalDateTime useDate;

    private BigDecimal companyId;

    private LocalDateTime syncTime;

    private String syncFlag;


}
