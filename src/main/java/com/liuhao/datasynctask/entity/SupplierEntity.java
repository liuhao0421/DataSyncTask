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
@TableName("supplier")
public class SupplierEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "supid", type = IdType.INPUT)
    private String supid;

    private String supname;

    private String address;

    private String tel;

    private String fax;

    private String contactman;

    private String email;

    private String chiefman;

    private String bankname;

    @TableField("bankAcctNo")
    private String bankAcctNo;

    private String businesstype;

    @TableField("settlementMode")
    private String settlementMode;

    private String settledays;

    private String paymethod;

    private String stocktaking;

    @TableField("orderWeekDay")
    private String orderWeekDay;

    private String sendweekday;

    private String predays;

    private String limitdays;

    private LocalDateTime createdate;

      @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedate;

    private String status;

    private String remark;

    private String purtype;

    private LocalDateTime syncTime;

    private String syncFlag;


}
