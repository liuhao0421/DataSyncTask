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
 * @since 2023-11-15
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
    private String disAmt;

    @TableField("payAmt")
    private String payAmt;

    @TableField("roundAmt")
    private String roundAmt;

    @TableField("cashAmt")
    private String cashAmt;

    @TableField("bankAmt")
    private String bankAmt;

    @TableField("cardAmt")
    private String cardAmt;

    @TableField("vipAmt")
    private String vipAmt;

    @TableField("couponAmt")
    private String couponAmt;

    @TableField("pointAmt")
    private String pointAmt;

    @TableField("aliPay")
    private String aliPay;

    @TableField("wechatPay")
    private String wechatPay;

    @TableField("bestPay")
    private String bestPay;

    @TableField("otherAmt")
    private String otherAmt;

    @TableField("otherMemo")
    private String otherMemo;

    @TableField("recCash")
    private String recCash;

    @TableField("retCash")
    private String retCash;

    @TableField("cardClearAmt")
    private String cardClearAmt;

    @TableField("cardDisAmt")
    private String cardDisAmt;

    @TableField("vipBalance")
    private String vipBalance;

    @TableField("outTradeNo")
    private String outTradeNo;

    private LocalDateTime syncTime;

    private String syncFlag;


}
