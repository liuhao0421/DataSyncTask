package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("sale_paymode")
public class SalePaymodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "braid", type = IdType.INPUT)
    private String braid;

    private String saleid;

    private String saletype;

    private LocalDateTime saledate;

    private LocalDateTime inputdate;

    private LocalDateTime uploaddate;

    private String sendflag;

    private String posno;

    private String salerid;

    private String saleamt;

    private String disamt;

    private String payamt;

    private String paydibs;

    private String cash;

    private String creditcard;

    private String prepaycard;

    private String mempay;

    private String shoppingticket;

    private String points;

    private String alipay;

    private String wxpay;

    private String bestpay;

    private String otheramt;

    private String othermemo;

    private String reccash;

    private String retamt;

    private String clearamt;

    private String iccardDisamt;

    private String membalance;

    private String payorderno;

    private LocalDateTime syncTime;

    private String syncFlag;


}
