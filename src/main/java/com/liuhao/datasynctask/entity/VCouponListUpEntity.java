package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
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

    private String auditdate;

    private String printdate;

      @TableField(fill = FieldFill.INSERT)
    private String startdate;

    private String enddate;

    private Double fullamt;

    private String pcashid;

    @TableField("pcashDescription")
    private String pcashDescription;

    private Double awardId;

      @TableField(fill = FieldFill.UPDATE)
    private String updatedate;

    private String useddate;

    private Double companyId;

    private String syncTime;

    private String syncFlag;


}
