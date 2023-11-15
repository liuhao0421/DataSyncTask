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
@TableName("sys_users")
public class SysUsersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    @TableField("StoreId")
    private String StoreId;

    @TableField("UserId")
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
    private String DiscountRate;

    @TableField("ChangePriceRate")
    private String ChangePriceRate;

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

    private String cardsalerate;

    private LocalDateTime syncTime;

    private String syncFlag;


}
