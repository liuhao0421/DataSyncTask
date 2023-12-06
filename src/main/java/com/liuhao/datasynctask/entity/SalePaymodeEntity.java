package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.*;

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

    @TableField("mem_id")
    private String memId;

    private String saleamt;

    private Double disamt;

    private Double payamt;

    private Double paydibs;

    private Double cash;

    private Double creditcard;

    private Double prepaycard;

    private Double mempay;

    private Double shoppingticket;

    private Double points;

    private Double alipay;

    private Double wxpay;

    private Double bestpay;

    private Double otheramt;

    private Double othermemo;

    private Double reccash;

    private Double retamt;

    private Double clearamt;

    private Double iccardDisamt;

    private Double membalance;

    private String payorderno;

    private LocalDateTime syncTime;

    private String syncFlag;


}
