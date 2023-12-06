package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    @Select("select * from member_card where sync_flag is null and sync_time is null and company_id =${companyId} limit 1000")
    List<MemberCardEntity> getData(@Param("companyId") String companyId);

    @Select("select * from member_card where sync_flag = '0' and sync_time < update_time and company_id =${companyId} limit 1000")
    List<MemberCardEntity> getUpdateData(@Param("companyId") String companyId);

    @Update("update member_card set sync_flag = 0 where sync_flag = 1 and company_id =${companyId}")
    void backSyncFalg(@Param("companyId") String companyId);
}
