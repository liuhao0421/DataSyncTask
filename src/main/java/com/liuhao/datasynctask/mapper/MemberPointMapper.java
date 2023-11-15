package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.entity.MemberPointEntity;
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
 * @since 2023-11-15
 */
@Mapper
public interface MemberPointMapper extends BaseMapper<MemberPointEntity> {
    @Select("select * from member_point where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < create_time) limit 1000")
    List<MemberPointEntity> getData();
}
