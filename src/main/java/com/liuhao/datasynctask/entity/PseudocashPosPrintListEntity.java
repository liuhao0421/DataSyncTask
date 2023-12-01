package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2023-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("PseudoCash_POS_Print_List")
public class PseudocashPosPrintListEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String braid;

    @TableField("PCashid")
    private String PCashid;

    private String pcashvalue;

    @TableField("POSNO")
    private String posno;

    private String saleid;

    private String salerid;

    private String saleamt;

    private String maintext;

    private LocalDateTime printdate;

    private String sequenceid;

    private Double reqamt;

    @TableField("Couponid")
    private String Couponid;

    private String status;

    @TableField("LimitCount")
    private String LimitCount;

    @TableField("UsedCount")
    private String UsedCount;

    private String seqamt;

    private String memId;

    private String formemberonly;

      @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedate;

    private LocalDateTime useddate;

    private Integer limitsum;

    private String pcashtype;

    private Double maxAmt;

    private LocalDateTime syncTime;

    private String syncFlag;


}
