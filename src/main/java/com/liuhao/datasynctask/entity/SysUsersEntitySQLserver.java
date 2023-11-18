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
@TableName("SYS_Users")
public class SysUsersEntitySQLserver implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("StoreId")
    private String StoreId;

      @TableId(value = "UserId", type = IdType.INPUT)
    private String UserId;

    @TableField("UserName")
    private String UserName;

    @TableField("Pwd")
    private String Pwd;

    @TableField("Status")
    private String Status;

    @TableField("Sex")
    private String Sex;

    @TableField("Birthday")
    private LocalDateTime Birthday;

    @TableField("Addr")
    private String Addr;

    @TableField("Tel")
    private String Tel;

    @TableField("Mobile")
    private String Mobile;

    @TableField("DiscountRate")
    private Double DiscountRate;

    @TableField("ChangePriceRate")
    private Double ChangePriceRate;

    @TableField("Remark")
    private String Remark;

    private LocalDateTime chgpwddate;

    @TableField("Leavedate")
    private LocalDateTime Leavedate;

    @TableField("CreateDate")
    private LocalDateTime CreateDate;

      @TableField(value = "UpdateDate", fill = FieldFill.UPDATE)
    private LocalDateTime UpdateDate;

    private String outCashierid;

    private Double cardsalerate;

    private LocalDateTime syncTime;

    private String syncFlag;


}
