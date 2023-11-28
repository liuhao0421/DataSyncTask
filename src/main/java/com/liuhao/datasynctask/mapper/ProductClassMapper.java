package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.ProductClassEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhao.datasynctask.entity.SupplierEntity;
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
public interface ProductClassMapper extends BaseMapper<ProductClassEntity> {
    @Select("select TOP (1000) * from Product_Class where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < updatedate)")
    List<ProductClassEntity> getData();
    @Update("update Product_Class set sync_flag = 0 where sync_flag = 1")
    void backSyncFalg();
}
