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
    private Double retRate;

    @TableField("saleQty")
    private Double saleQty;

    @TableField("saleAmt")
    private Double saleAmt;

    @TableField("salePrice")
    private Double salePrice;

    @TableField("avgSalePrice")
    private Double avgSalePrice;

    @TableField("saleCostPrice")
    private Double saleCostPrice;

    @TableField("saleCostAmt")
    private Double saleCostAmt;

    @TableField("saleDisAmt")
    private Double saleDisAmt;

    @TableField("saleProfit")
    private Double saleProfit;

    @TableField("promtQty")
    private Double promtQty;

    @TableField("promtAmt")
    private Double promtAmt;

    @TableField("promtCostAmt")
    private Double promtCostAmt;

    @TableField("promtDisAmt")
    private Double promtDisAmt;

    @TableField("promtProfit")
    private Double promtProfit;

    @TableField("vipQty")
    private Double vipQty;

    @TableField("vipAmt")
    private Double vipAmt;

    @TableField("vipCostAmt")
    private Double vipCostAmt;

    @TableField("vipDisAmt")
    private Double vipDisAmt;

    @TableField("vipProfit")
    private Double vipProfit;

    @TableField("retQty")
    private String retQty;

    @TableField("retAmt")
    private String retAmt;

    private LocalDateTime syncTime;

    private String syncFlag;


}
