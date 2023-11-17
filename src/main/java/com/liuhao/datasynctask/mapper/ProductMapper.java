package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.ProductEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhao.datasynctask.entity.VCouponListUpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
public interface ProductMapper extends BaseMapper<ProductEntity> {
    @Select("select TOP (1000) * from product where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < updatedate)")
    List<ProductEntity> getData();
}
