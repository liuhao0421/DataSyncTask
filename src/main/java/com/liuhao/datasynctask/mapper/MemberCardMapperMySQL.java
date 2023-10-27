package com.liuhao.datasynctask.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.MemberCardEntityMySQL;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhao
 * @since 2023-10-26
 */
@Mapper
@DS("mysql")
public interface MemberCardMapperMySQL extends BaseMapper<MemberCardEntityMySQL> {

}
