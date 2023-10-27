package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2023-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_card")
public class MemberCardEntityMySQL implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "mem_id", type = IdType.INPUT)
    private String memId;

    private String cardId;

    private String companyId;

    private String braid;

    private String cardFrom;

    private String cardStatus;

    private String appid;

    private String appname;

    private String cardType;

    private String name;

    private String sex;

    private String nation;

    private String birthday;

    private String idcardAddress;

    private String address;

    private String idcardno;

    private String occupation;

    private String phone1;

    private String totalcredit;

    private String totaldebit;

    private String balance;

    private String debitnumber;

    private String totalpoint;

    private String exchangepoint;

    private String overduepoint;

    private String usefulpoint;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String comment;

    private String openid;

    private String province;

    private String city;

    private String county;

    private String validTime;

    private String pwdenabled;

    private String userpwd;

    private String binddate;

    private String userSalt;

    private String shareCode;

    private String inviteMemId;


}
