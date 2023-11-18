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
@TableName("Product_Class")
public class ProductClassEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

      @TableId(value = "classid", type = IdType.INPUT)
    private String classid;

    private String classname;

    private String parentcode;

    private String level;

    private String haschild;

    private String potflag;

    private String status;

    private Double points;

    private LocalDateTime createdate;

      @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedate;

    private String status1;

    private LocalDateTime syncTime;

    private String syncFlag;


}
