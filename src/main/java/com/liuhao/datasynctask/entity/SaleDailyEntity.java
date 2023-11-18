package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sale_daily")
public class SaleDailyEntity implements Serializable {

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

    private String batch;

    private String proid;

    private String barcode;

    private String barcodeBulk;

    @TableField("invoiceId")
    private String invoiceId;

    private String batchrowid;

    private Double saleqty;

    private Double saleamt;

    private Double saledisamt;

    @TableField("LastcostPrice")
    private Double LastcostPrice;

    @TableField("costPrice")
    private Double costPrice;

    private Double normalprice;

    private Double ncostprice;

    @TableField("curPrice")
    private Double curPrice;

    private Double points1;

    private Double classid;

    private String brandid;

    private String supid;

    private String producttype;

    private String stockflag;

    private Double returnrat;

    private Double points;

    private String memcardno;

    private String productpmtplanno;

    private String brandclasspmtplanno;

    private String transpmtplanno;

    private String donatetype;

    private Double pmtqty;

    @TableField("disExchange")
    private String disExchange;

    private String overlap;

    private String present;

    private String isinpromt;

    private String pmtrowid;

    private String retsaleid;

    private String accreditReturn;

    private String accreditDisamt;

    private LocalDateTime syncTime;

    private String syncFlag;


}
