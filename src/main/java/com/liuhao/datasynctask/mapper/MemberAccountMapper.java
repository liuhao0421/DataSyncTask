package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.MemberAccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhao.datasynctask.entity.MemberCardEntity;
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
public interface MemberAccountMapper extends BaseMapper<MemberAccountEntity> {
    @Select("select * from member_account where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < lastupdate) limit 1000")
    List<MemberAccountEntity> getUpdateData();
}
