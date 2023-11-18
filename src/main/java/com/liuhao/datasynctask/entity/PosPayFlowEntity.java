package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("posPayFlow")
public class PosPayFlowEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    private String storeid;

    @TableField("saleId")
    private String saleId;

    @TableField("saleType")
    private String saleType;

    @TableField("saleDate")
    private LocalDateTime saleDate;

    @TableField("inputDate")
    private LocalDateTime inputDate;

    @TableField("uploadDate")
    private LocalDateTime uploadDate;

    @TableField("netStatus")
    private String netStatus;

    private String posno;

    @TableField("cashierId")
    private String cashierId;

    @TableField("memId")
    private String memId;

    @TableField("saleAmt")
    private String saleAmt;

    @TableField("disAmt")
    private Double disAmt;

    @TableField("payAmt")
    private Double payAmt;

    @TableField("roundAmt")
    private Double roundAmt;

    @TableField("cashAmt")
    private Double cashAmt;

    @TableField("bankAmt")
    private Double bankAmt;

    @TableField("cardAmt")
    private Double cardAmt;

    @TableField("vipAmt")
    private Double vipAmt;

    @TableField("couponAmt")
    private Double couponAmt;

    @TableField("pointAmt")
    private Double pointAmt;

    @TableField("aliPay")
    private Double aliPay;

    @TableField("wechatPay")
    private Double wechatPay;

    @TableField("bestPay")
    private Double bestPay;

    @TableField("otherAmt")
    private Double otherAmt;

    @TableField("otherMemo")
    private Double otherMemo;

    @TableField("recCash")
    private Double recCash;

    @TableField("retCash")
    private Double retCash;

    @TableField("cardClearAmt")
    private Double cardClearAmt;

    @TableField("cardDisAmt")
    private Double cardDisAmt;

    @TableField("vipBalance")
    private Double vipBalance;

    @TableField("outTradeNo")
    private String outTradeNo;

    private LocalDateTime syncTime;

    private String syncFlag;


}
