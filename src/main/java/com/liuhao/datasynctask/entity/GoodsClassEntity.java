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
 * @since 2023-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("GoodsClass")
public class GoodsClassEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    private String id;

    @TableField("ClassId")
    private String ClassId;

    @TableField("ClassName")
    private String ClassName;

    @TableField("ParentCode")
    private String ParentCode;

    @TableField("ClassLevel")
    private String ClassLevel;

    @TableField("HasChild")
    private String HasChild;

    @TableField("Isfresh")
    private String Isfresh;

    @TableField("TouchVisible")
    private String TouchVisible;

    private String status;

    private String pointrate;

    @TableField("CreateDate")
    private LocalDateTime CreateDate;

      @TableField(value = "UpdateDate", fill = FieldFill.UPDATE)
    private LocalDateTime UpdateDate;

    private LocalDateTime syncTime;

    private String syncFlag;


}
