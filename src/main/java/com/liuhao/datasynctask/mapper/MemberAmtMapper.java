package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.MemberAmtEntity;
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
 * @since 2023-11-02
 */
@Mapper
public interface MemberAmtMapper extends BaseMapper<MemberAmtEntity> {
    @Select("select  * from member_amt where (sync_flag = 0 or sync_flag is null) and  (sync_time < create_time or sync_time is null) limit 1000")
    List<MemberAmtEntity> getData();
}
