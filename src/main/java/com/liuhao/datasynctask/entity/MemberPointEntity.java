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
@TableName("member_point")
public class MemberPointEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "company_id", type = IdType.INPUT)
    private BigDecimal companyId;

    private String pointId;

    private String braid;

    private String memId;

    private String cardId;

    private String appid;

    private String appname;

    private String pointType;

    private String amountId;

    private BigDecimal amount;

    private BigDecimal points;

    private String createBy;

    private LocalDateTime createTime;

    private LocalDateTime synch;

    private String comment;

    private String ischecked;

    private String posno;

    private String sendflag;

    private LocalDateTime uploaddate;

    private LocalDateTime syncTime;

    private String syncFlag;


}
