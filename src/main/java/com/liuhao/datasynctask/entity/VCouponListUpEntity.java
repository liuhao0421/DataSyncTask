package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhao
 * @since 2023-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("v_Coupon_List_Up")
public class VCouponListUpEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "appid", type = IdType.INPUT)
    private String appid;

    private String openid;

    private String couponid;

    private Double pcashvalue;

    private String coupontype;

    private String status;

    private String source;

    private LocalDateTime auditdate;

    private LocalDateTime printdate;

      @TableField(fill = FieldFill.INSERT)
    private LocalDateTime startdate;

    private LocalDateTime enddate;

    private Double fullamt;

    private String pcashid;

    @TableField("pcashDescription")
    private String pcashDescription;

    private Double awardId;

      @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedate;

    private LocalDateTime useddate;

    private Double companyId;

    private LocalDateTime syncTime;

    private String syncFlag;


}
