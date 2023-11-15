package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2023-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_barcode")
public class ProductBarcodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "proid", type = IdType.INPUT)
    private String proid;

    private String barcode;

    private String pluno;

    private String labelformat;

    private String mainflag;

    private String createdate;

      @TableField(fill = FieldFill.UPDATE)
    private String updatedate;

    private String barmode;

    private String syncTime;

    private String syncFlag;


}
