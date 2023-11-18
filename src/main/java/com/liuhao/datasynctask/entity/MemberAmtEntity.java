package com.liuhao.datasynctask.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("member_amt_test")
public class MemberAmtEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private BigDecimal companyId;

    private String amtId;

    private String memId;

    private String cardId;

    private String braid;

    private String appid;

    private String appname;

    private String cardFrom;

    private String adjustType;

    private BigDecimal oldamount;

    private BigDecimal amount;

    private String preferentialType;

    private BigDecimal preferentialAmount;

    private BigDecimal pay;

    private BigDecimal balance;

    private String amountId;

    private String createBy;

    private LocalDateTime createTime;

    private String comment;

    private String isreturned;

    private String paymethod;

    private LocalDateTime syncTime;

    private String syncFlag;


}
