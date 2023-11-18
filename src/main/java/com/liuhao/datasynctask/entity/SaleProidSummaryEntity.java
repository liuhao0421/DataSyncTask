package com.liuhao.datasynctask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("sale_proid_summary")
public class SaleProidSummaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "braid", type = IdType.INPUT)
    private String braid;

    private LocalDateTime saledate;

    private String proid;

    private String supid;

    private Double returnrat;

    private Double saleqty;

    private Double saleamt;

    private Double avgsaleprice;

    private Double salecostprice;

    private Double salecostamt;

    private Double saledisamt;

    private Double saleprofit;

    private Double pmtqty;

    private Double pmtamt;

    private Double pmtcostamt;

    private Double pmtdisamt;

    private Double pmtprofit;

    private Double memqty;

    private Double memamt;

    private Double memcostamt;

    private Double memdisamt;

    private Double memprofit;

    private LocalDateTime syncTime;

    private String syncFlag;


}
