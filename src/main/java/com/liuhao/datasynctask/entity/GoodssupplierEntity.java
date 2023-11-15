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
@TableName("goodssupplier")
public class GoodssupplierEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    @TableField("supId")
    private String supId;

    @TableField("supName")
    private String supName;

    private String addr;

    private String phone;

    private String fax;

    private String contacts;

    private String email;

    private String legalperson;

    @TableField("BusinessLicence")
    private String BusinessLicence;

    @TableField("bankName")
    private String bankName;

    @TableField("bankAccount")
    private String bankAccount;

    @TableField("bankPhone")
    private String bankPhone;

    @TableField("supplyMode")
    private String supplyMode;

    @TableField("SettlementMode")
    private String SettlementMode;

    @TableField("SettlementDays")
    private String SettlementDays;

    @TableField("payMode")
    private String payMode;

    @TableField("stockType")
    private String stockType;

    @TableField("orderWeekDay")
    private String orderWeekDay;

    @TableField("deliveryWeekDay")
    private String deliveryWeekDay;

    @TableField("deliveryDays")
    private String deliveryDays;

    @TableField("orderExpireDays")
    private String orderExpireDays;

    @TableField("operId")
    private String operId;

    @TableField("createDate")
    private LocalDateTime createDate;

    @TableField("updateUser")
    private String updateUser;

      @TableField(value = "updateDate", fill = FieldFill.UPDATE)
    private LocalDateTime updateDate;

    private String status;

    private String remark;

    @TableField("isStrongRelation")
    private String isStrongRelation;

    private LocalDateTime syncTime;

    private String syncFlag;


}
