package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.ProductClassEntity;
import com.liuhao.datasynctask.entity.SaleDailyEntity;
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
public interface SaleDailyMapper extends BaseMapper<SaleDailyEntity> {
    @Select("select TOP (1000) * from sale_daily where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < uploaddate)")
    List<SaleDailyEntity> getData();
    @Update("update sale_daily set sync_flag = 0 where sync_flag = 1")
    void backSyncFalg();
}
