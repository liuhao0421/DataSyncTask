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
@TableName("posGoodsFlow")
public class PosGoodsFlowEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    @TableField("storeId")
    private String storeId;

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

    private String batchid;

    @TableField("goodsId")
    private String goodsId;

    private String barcode;

    @TableField("inputBar")
    private String inputBar;

    @TableField("priceQtyFlag")
    private String priceQtyFlag;

    private String rowid;

    @TableField("saleQty")
    private Double saleQty;

    @TableField("saleAmt")
    private Double saleAmt;

    @TableField("saleDisAmt")
    private Double saleDisAmt;

    @TableField("inPrice")
    private Double inPrice;

    @TableField("costPrice")
    private Double costPrice;

    @TableField("salePrice")
    private Double salePrice;

    @TableField("vipPrice")
    private Double vipPrice;

    @TableField("curPrice")
    private Double curPrice;

    @TableField("curPoint")
    private Double curPoint;

    private Double classid;

    private String brandid;

    private String supid;

    @TableField("supplyMode")
    private String supplyMode;

    private String stocktype;

    @TableField("retType")
    private String retType;

    @TableField("retRate")
    private Double retRate;

    @TableField("vipPoint")
    private Double vipPoint;

    @TableField("memId")
    private String memId;

    @TableField("promtNo1")
    private String promtNo1;

    @TableField("promtNo2")
    private String promtNo2;

    @TableField("promtNo3")
    private String promtNo3;

    @TableField("promtType")
    private String promtType;

    @TableField("promtQty")
    private Double promtQty;

    @TableField("promtDisType")
    private String promtDisType;

    @TableField("promtSaleOnSale")
    private String promtSaleOnSale;

    @TableField("promtIsPresent")
    private String promtIsPresent;

    @TableField("promtIsInPromt")
    private String promtIsInPromt;

    @TableField("promtRowId")
    private String promtRowId;

    @TableField("retSaleId")
    private String retSaleId;

    @TableField("retAccId")
    private String retAccId;

    @TableField("disAmtAccId")
    private String disAmtAccId;

    private LocalDateTime syncTime;

    private String syncFlag;


}
