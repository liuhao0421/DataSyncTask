package com.liuhao.datasynctask.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.PseudocashPosPrintListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhao
 * @since 2023-11-25
 */
@Mapper
@DS("sqlserver")
public interface PseudocashPosPrintListMapper extends BaseMapper<PseudocashPosPrintListEntity> {
    @Update("update PseudoCash_POS_Print_List set sync_flag = 0 where sync_flag = 1")
    void backSyncFalg();
}
