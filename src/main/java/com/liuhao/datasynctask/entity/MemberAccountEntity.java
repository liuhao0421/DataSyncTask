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
@TableName("member_account")
public class MemberAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "mem_id", type = IdType.INPUT)
    private String memId;

    private BigDecimal initpoint;

    private BigDecimal totalpoint;

    private BigDecimal usefulpoint;

    private LocalDateTime lastupdate;

    private LocalDateTime syncTime;

    private String syncFlag;


}
