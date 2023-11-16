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
@TableName("goodsmulticode")
public class GoodsmulticodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    private String goodsid;

    private String barcode;

    private String pluno;

    @TableField("labelFormat")
    private String labelFormat;

    @TableField("isMain")
    private String isMain;

    @TableField("createDate")
    private LocalDateTime createDate;

      @TableField(value = "updateDate", fill = FieldFill.UPDATE)
    private LocalDateTime updateDate;

    @TableField("barType")
    private String barType;

    private LocalDateTime syncTime;

    private String syncFlag;


}
