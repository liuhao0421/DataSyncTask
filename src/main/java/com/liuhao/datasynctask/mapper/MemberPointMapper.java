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
 * @since 2023-11-05
 */
@Mapper
public interface MemberPointMapper extends BaseMapper<MemberPointEntity> {
    @Select("select TOP (1000)  * from member_point where (sync_flag = 0 or sync_flag is null) and  (sync_time < create_time or sync_time is null)")
    List<MemberPointEntity> getData();
}
