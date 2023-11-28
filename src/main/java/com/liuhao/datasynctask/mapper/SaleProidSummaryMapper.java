package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.SalePaymodeEntity;
import com.liuhao.datasynctask.entity.SaleProidSummaryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhao
 * @since 2023-11-05
 */
@Mapper
public interface SaleProidSummaryMapper extends BaseMapper<SaleProidSummaryEntity> {
    @Select("select TOP (1000) * from sale_proid_summary where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < saledate)")
    List<SaleProidSummaryEntity> getData();
    @Update("update sale_proid_summary set sync_flag = 0 where sync_flag = 1")
    void backSyncFalg();
}
