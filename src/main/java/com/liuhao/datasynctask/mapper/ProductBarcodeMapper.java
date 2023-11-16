package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.ProductBarcodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ProductBarcodeMapper extends BaseMapper<ProductBarcodeEntity> {
    @Select("select * from product_barcode where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < updatedate) limit 1000")
    List<ProductBarcodeEntity> getData();
}
