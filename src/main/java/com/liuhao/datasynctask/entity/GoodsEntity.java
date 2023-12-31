package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
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
 * @since 2023-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goods")
public class GoodsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    private String goodsid;

    private String barcode;

    private String goodsname;

    private String unitno;

    private Double inprice;

    private Double saleprice;

    @TableField("vipPrice")
    private Double vipPrice;

    @TableField("vipPoint")
    private Double vipPoint;

    private String spec;

    private String area;

    private String grade;

    private String classid;

    private String brandid;

    private String supid;

    private Double intax;

    private Double saletax;

    @TableField("helpCode")
    private String helpCode;

    private Double boxrate;

    @TableField("minOrderQty")
    private Double minOrderQty;

    @TableField("barType")
    private String barType;

    @TableField("proType")
    private String proType;

    @TableField("supplyMode")
    private String supplyMode;

    @TableField("canScalechgPrice")
    private String canScalechgPrice;

    @TableField("vipDisType")
    private String vipDisType;

    @TableField("minDisrate")
    private Double minDisrate;

    @TableField("printMode")
    private String printMode;

    @TableField("shelfDay")
    private String shelfDay;

    private String seasonal;

    @TableField("retType")
    private String retType;

    @TableField("retRate")
    private Double retRate;

    @TableField("alarmDays")
    private String alarmDays;

    @TableField("cpsType")
    private Double cpsType;

    @TableField("cpsRate")
    private String cpsRate;

    @TableField("stockType")
    private String stockType;

    @TableField("operId")
    private String operId;

    private LocalDateTime createdate;

    @TableField("lastUpdateUser")
    private String lastUpdateUser;

      @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedate;

    private LocalDateTime stopdate;

    private String status;

    private String ingredient;

    @TableField("saleMethod")
    private String saleMethod;

    private LocalDateTime syncTime;

    private String syncFlag;


}
