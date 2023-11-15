package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Scope;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhao
 * @since 2023-11-14
 */
@Mapper
public interface MemberCardMapper extends BaseMapper<MemberCardEntity> {
    @Select("select * from member_card where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < update_time) limit 1000")
    List<MemberCardEntity> getData();

    @Select("select * from member_card where sync_flag = '0' and sync_time < update_time limit 1000")
    List<MemberCardEntity> getUpdateData();
}
