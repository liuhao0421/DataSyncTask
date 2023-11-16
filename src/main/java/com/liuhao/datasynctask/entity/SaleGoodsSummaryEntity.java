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
 * @since 2023-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("saleGoodsSummary")
public class SaleGoodsSummaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    @TableField("storeId")
    private String storeId;

    private LocalDateTime saledate;

    @TableField("goodsId")
    private String goodsId;

    private String supid;

    @TableField("retType")
    private String retType;

    @TableField("retRate")
    private String retRate;

    @TableField("saleQty")
    private String saleQty;

    @TableField("saleAmt")
    private String saleAmt;

    @TableField("salePrice")
    private String salePrice;

    @TableField("avgSalePrice")
    private String avgSalePrice;

    @TableField("saleCostPrice")
    private String saleCostPrice;

    @TableField("saleCostAmt")
    private String saleCostAmt;

    @TableField("saleDisAmt")
    private String saleDisAmt;

    @TableField("saleProfit")
    private String saleProfit;

    @TableField("promtQty")
    private String promtQty;

    @TableField("promtAmt")
    private String promtAmt;

    @TableField("promtCostAmt")
    private String promtCostAmt;

    @TableField("promtDisAmt")
    private String promtDisAmt;

    @TableField("promtProfit")
    private String promtProfit;

    @TableField("vipQty")
    private String vipQty;

    @TableField("vipAmt")
    private String vipAmt;

    @TableField("vipCostAmt")
    private String vipCostAmt;

    @TableField("vipDisAmt")
    private String vipDisAmt;

    @TableField("vipProfit")
    private String vipProfit;

    @TableField("retQty")
    private String retQty;

    @TableField("retAmt")
    private String retAmt;

    private LocalDateTime syncTime;

    private String syncFlag;


}
