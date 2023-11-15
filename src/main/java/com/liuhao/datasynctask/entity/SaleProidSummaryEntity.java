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
 * @since 2023-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sale_proid_summary")
public class SaleProidSummaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "braid", type = IdType.INPUT)
    private String braid;

    private String saledate;

    private String proid;

    private String supid;

    private String returnrat;

    private String saleqty;

    private String saleamt;

    private String avgsaleprice;

    private String salecostprice;

    private String salecostamt;

    private String saledisamt;

    private String saleprofit;

    private String pmtqty;

    private String pmtamt;

    private String pmtcostamt;

    private String pmtdisamt;

    private String pmtprofit;

    private String memqty;

    private String memamt;

    private String memcostamt;

    private String memdisamt;

    private String memprofit;

    private String syncTime;

    private String syncFlag;


}
