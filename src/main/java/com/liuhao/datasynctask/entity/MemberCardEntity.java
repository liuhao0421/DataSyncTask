package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;

import java.math.BigDecimal;
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
 * @since 2023-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_card_test")
public class MemberCardEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mem_id", type = IdType.INPUT)
    private String memId;

    private String cardId;

    private BigDecimal companyId;

    private BigDecimal sourceId;

    private String braid;

    /**
     * 1-超市注册会员(线下)   2-手机注册会员(线上)
     */
    private String cardFrom;

    /**
     * 已启用（批量卡第一次消费或单卡发卡） U-use
     过期 O-over
     作废 S-stop
     挂失 L-lock

     充值和消费在O S L都不允许
     */
    private String cardStatus;

    private String appid;

    private String appname;

    /**
     * 会员卡类型主要分2类： 金卡、银卡、普通卡  或 超市有自己的命名方式 ，针对卡类型的后期功能可能需要定制或定改
     */
    private String cardType;

    private String name;

    /**
     * 0：男    1：女     2：未知
     */
    private String sex;

    private String nation;

    private LocalDateTime birthday;

    private String idcardAddress;

    private String address;

    private String idcardno;

    private String occupation;

    private String phone1;

    private String phone2;

    private BigDecimal totalcredit;

    private BigDecimal totaldebit;

    /**
     * 余额
     */
    private BigDecimal balance;

    private Integer debitnumber;

    private BigDecimal totalpoint;

    private BigDecimal exchangepoint;

    private BigDecimal overduepoint;

    private BigDecimal usefulpoint;

    /**
     * 直接显示名字
     */
    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String comment;

    /**
     * 同一个公众号下一个用户的openid只会有一个
     */
    private String openid;

    private String province;

    private String city;

    private String county;

    private LocalDateTime validTime;

    private BigDecimal initpoint;

    /**
     * 用户盐，用于生成加密串
     */
    private String userSalt;

    /**
     * MD5魔数混合加密
     */
    private String shareCode;

    /**
     * 邀请人id
     */
    private String inviteMemId;

    private String userpwd;

    private String pwdenabled;

    private LocalDateTime syncTime;

    private String syncFlag;

    @Override
    public String toString() {
        return "MemberCardEntity{" +
                "memId='" + memId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", companyId=" + companyId +
                ", sourceId=" + sourceId +
                ", braid='" + braid + '\'' +
                ", cardFrom='" + cardFrom + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                ", appid='" + appid + '\'' +
                ", appname='" + appname + '\'' +
                ", cardType='" + cardType + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", birthday=" + birthday +
                ", idcardAddress='" + idcardAddress + '\'' +
                ", address='" + address + '\'' +
                ", idcardno='" + idcardno + '\'' +
                ", occupation='" + occupation + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", totalcredit=" + totalcredit +
                ", totaldebit=" + totaldebit +
                ", balance=" + balance +
                ", debitnumber=" + debitnumber +
                ", totalpoint=" + totalpoint +
                ", exchangepoint=" + exchangepoint +
                ", overduepoint=" + overduepoint +
                ", usefulpoint=" + usefulpoint +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", comment='" + comment + '\'' +
                ", openid='" + openid + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", validTime=" + validTime +
                ", initpoint=" + initpoint +
                ", userSalt='" + userSalt + '\'' +
                ", shareCode='" + shareCode + '\'' +
                ", inviteMemId='" + inviteMemId + '\'' +
                ", userpwd='" + userpwd + '\'' +
                ", pwdenabled='" + pwdenabled + '\'' +
                ", syncTime=" + syncTime +
                ", syncFlag='" + syncFlag + '\'' +
                '}';
    }
}
