package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.MemberAccountEntity;
import com.liuhao.datasynctask.entity.MemberAccountEntity;
import com.liuhao.datasynctask.mapper.MemberAccountMapper;
import com.liuhao.datasynctask.mapper.MemberAccountMapper;
import com.liuhao.datasynctask.service.MemberAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhao
 * @since 2023-11-05
 */
@Service
@Slf4j
public class MemberAccountServiceImpl extends ServiceImpl<MemberAccountMapper, MemberAccountEntity> implements MemberAccountService {


    @Autowired
    private MemberAccountMapper memberAccountMapper;

    
    
    //获取数据源修改的数据
    @Override
    @DS("sqlserver")
    public String getUpdateDataFromSource() {
        List<MemberAccountEntity> memberAccountEntityList = null;
        try{
            //读取需要同步的数据
            memberAccountEntityList =  memberAccountMapper.getUpdateData();
            //将读取了的数据，做标志
            for (MemberAccountEntity memberAccountEntity : memberAccountEntityList) {
                memberAccountEntity.setSyncFlag("1");
                memberAccountMapper.updateById(memberAccountEntity);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(memberAccountEntityList==null||memberAccountEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(memberAccountEntityList);
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            MemberAccountEntity memberAccountEntity = JSONObject.parseObject(sourceData, MemberAccountEntity.class);
            memberAccountEntity.setSyncTime(LocalDateTime.now());
            memberAccountEntity.setSyncFlag("0");
            memberAccountMapper.updateById(memberAccountEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }


    //将修改的数据同步至目标表
    @Override
    @DS("mysql")
    public String updateTargetData(String sourceData) {
        try{
            MemberAccountEntity memberAccountEntity = JSONObject.parseObject(sourceData, MemberAccountEntity.class);
            memberAccountEntity.setSyncTime(LocalDateTime.now());
            memberAccountMapper.updateById(memberAccountEntity);
            return JSONObject.toJSONString(memberAccountEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
