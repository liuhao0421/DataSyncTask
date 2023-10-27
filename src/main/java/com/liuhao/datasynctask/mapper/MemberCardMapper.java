package com.liuhao.datasynctask.mapper;



import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhao.datasynctask.entity.MemberCard;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhao
 * @since 2023-10-25
 */
@Mapper
@DS("sqlserver")
public interface MemberCardMapper extends BaseMapper<MemberCard> {

}
