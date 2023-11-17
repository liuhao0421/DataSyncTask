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
 * @since 2023-11-05
 */
@Mapper
public interface MemberAccountMapper extends BaseMapper<MemberAccountEntity> {
    @Select("select TOP (1000)  * from member_account where (sync_flag = 0 or sync_flag is null) and  (sync_time < lastupdate or sync_time is null)")
    List<MemberAccountEntity> getUpdateData();
}
