package com.liuhao.datasynctask.service.impl;

import com.liuhao.datasynctask.entity.MemberCardEntityMySQL;
import com.liuhao.datasynctask.mapper.MemberCardMapperMySQL;
import com.liuhao.datasynctask.service.MemberCardServiceMySQL;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhao
 * @since 2023-10-26
 */
@Service
public class MemberCardServiceImplMySQL extends ServiceImpl<MemberCardMapperMySQL, MemberCardEntityMySQL> implements MemberCardServiceMySQL {

}
