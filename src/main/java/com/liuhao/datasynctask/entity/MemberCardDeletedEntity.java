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
 * @since 2023-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_card_deleted")
public class MemberCardDeletedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "mem_id", type = IdType.INPUT)
    private String memId;

    private String cardId;

    private BigDecimal companyId;

    private String braid;

    private String cardFrom;

    private String cardStatus;

    private String appid;

    private String appname;

    private String cardType;

    private String name;

    private String sex;

    private String nation;

    private LocalDateTime birthday;

    private String idcardAddress;

    private String address;

    private String idcardno;

    private String occupation;

    private String phone1;

    private BigDecimal totalcredit;

    private BigDecimal totaldebit;

    private BigDecimal balance;

    private BigDecimal debitnumber;

    private BigDecimal totalpoint;

    private BigDecimal exchangepoint;

    private BigDecimal overduepoint;

    private BigDecimal usefulpoint;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String comment;

    private String openid;

    private String province;

    private String city;

    private String county;

    private LocalDateTime validTime;

    private String pwdenabled;

    private String userpwd;

    private LocalDateTime binddate;

    private String userSalt;

    private String shareCode;

    private String inviteMemId;

    private LocalDateTime syncTime;

    private String syncFlag;


}
