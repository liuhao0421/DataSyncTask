package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.SaleProidSummaryEntity;
import com.liuhao.datasynctask.entity.SysUsersEntitySQLserver;
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
public interface SysUsersMapperSQLserver extends BaseMapper<SysUsersEntitySQLserver> {
    @Select("select TOP (1000) * from SYS_Users where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < UpdateDate)")
    List<SysUsersEntitySQLserver> getData();
}
