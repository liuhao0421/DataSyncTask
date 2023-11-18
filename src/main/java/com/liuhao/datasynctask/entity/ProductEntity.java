package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "proid", type = IdType.INPUT)
    private String proid;

    private String barcode;

    private String proname;

    private String measureid;

    private Double taxprice;

    private Double normalprice;

    private Double memberprice;

    private Double cardpoint;

    private String spec;

    private String area;

    private String grade;

    private String classid;

    private String brandid;

    private String supid;

    private Double intax;

    private Double saletax;

    @TableField("Helpcode")
    private String Helpcode;

    private Double packetqty;

    private Double minorderqty;

    private String barmode;

    @TableField("ProType")
    private String ProType;

    private String proflag;

    private String udf3;

    private String potflag;

    @TableField("MinDisrate")
    private Double MinDisrate;

    private String weightflag;

    private String warrantydays;

    @TableField("Seasonal")
    private String Seasonal;

    @TableField("Returntype")
    private String Returntype;

    private Double returnrat;

    private String alarmdays;

    private String cpstype;

    private Double cpsrate;

    private String stocktaking;

    private String operatorid;

    private LocalDateTime createdate;

    @TableField("LastUpdateUser")
    private String LastUpdateUser;

      @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedate;

    private LocalDateTime stopdate;

    private String status;

    @TableField("Material")
    private String Material;

    @TableField("saleMethod")
    private String saleMethod;

    private LocalDateTime syncTime;

    private String syncFlag;


}
