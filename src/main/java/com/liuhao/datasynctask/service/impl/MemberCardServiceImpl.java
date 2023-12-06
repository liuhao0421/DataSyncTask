package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.MemberCardDeletedEntity;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.MemberCardDeletedMapper;
import com.liuhao.datasynctask.mapper.MemberCardMapper;
import com.liuhao.datasynctask.service.MemberCardService;
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
 * @since 2023-11-14
 */
@Service
@Slf4j
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCardEntity> implements MemberCardService {

    @Autowired
    private MemberCardMapper memberCardMapper;
    @Autowired
    private MemberCardDeletedMapper memberCardDeletedMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源修改的数据
    @Override
    @DS("mysql")
    public String getUpdateDataFromSource(String companyId) {
        List<MemberCardEntity> memberCardEntityList = null;
        try{
            //读取需要同步的数据
            memberCardEntityList =  memberCardMapper.getUpdateData(companyId);
            //将读取了的数据，做标志
            for (MemberCardEntity memberCardEntity : memberCardEntityList) {
                memberCardEntity.setSyncFlag("1");
                memberCardMapper.updateById(memberCardEntity);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(memberCardEntityList==null||memberCardEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(memberCardEntityList);
        }
    }

    //获取数据源新增的数据
    @Override
    @DS("mysql")
    public String getDataFromSource(String companyId) {
        List<MemberCardEntity> memberCardEntityList = null;
        try{
            //读取需要同步的数据
            memberCardEntityList =  memberCardMapper.getData(companyId);
            //将读取了的数据，做标志
            for (MemberCardEntity memberCardEntity : memberCardEntityList) {
                memberCardEntity.setSyncFlag("1");
                memberCardMapper.updateById(memberCardEntity);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(memberCardEntityList==null||memberCardEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(memberCardEntityList);
        }
    }


    @Override
    @DS("sqlserver")
    public String selectIsExist(String sourceData) {
        try{
            MemberCardEntity memberCardEntity = JSONObject.parseObject(sourceData, MemberCardEntity.class);
            QueryWrapper<MemberCardEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("mem_id",memberCardEntity.getMemId());
            MemberCardEntity memberCardEntityResult = memberCardMapper.selectOne(queryWrapper);
            if(memberCardEntityResult==null){
                return null;
            }else{
                return JSONObject.toJSONString(memberCardEntityResult);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    //将新增的数据同步至目标表
    @Override
    @DS("sqlserver")
    public String pushDataToTarget(String sourceData) {
        try{
            MemberCardEntity memberCardEntity = JSONObject.parseObject(sourceData, MemberCardEntity.class);
            memberCardEntity.setSyncTime(LocalDateTime.now());
            memberCardMapper.insert(memberCardEntity);
            return JSONObject.toJSONString(memberCardEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    //同步结束后修改数据源表的状态
    @Override
    @DS("mysql")
    public void updateSourceData(String sourceData) {
        try{
            MemberCardEntity memberCardEntity = JSONObject.parseObject(sourceData, MemberCardEntity.class);
            memberCardEntity.setSyncTime(LocalDateTime.now());
            memberCardEntity.setSyncFlag("0");
            memberCardMapper.updateById(memberCardEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }


    //将修改的数据同步至目标表
    @Override
    @DS("sqlserver")
    public String updateTargetData(String sourceData) {
        try{
            MemberCardEntity memberCardEntity = JSONObject.parseObject(sourceData, MemberCardEntity.class);
            memberCardEntity.setSyncTime(LocalDateTime.now());
            memberCardMapper.updateById(memberCardEntity);
            return JSONObject.toJSONString(memberCardEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    @DS("mysql")
    public void backSyncFalg(String companyId) {
        memberCardMapper.backSyncFalg(companyId);
    }

    @Override
    @DS("mysql")
    public void beforeDelete(String Data) {
        //在member_card_deleted表中将要删除的数据备份
        MemberCardDeletedEntity memberCardDeletedEntity = JSONObject.parseObject(Data, MemberCardDeletedEntity.class);
        if(beginHandler.getCompanId().equals(memberCardDeletedEntity.getCompanyId())){
            memberCardDeletedMapper.insert(memberCardDeletedEntity);
        }
    }

    @Override
    @DS("sqlserver")
    public void localDelete(String Data) {
        //将member_card表中的该条记录删除
        MemberCardEntity memberCardEntity = JSONObject.parseObject(Data, MemberCardEntity.class);
        if(beginHandler.getCompanId().equals(memberCardEntity.getCompanyId())){
            memberCardMapper.deleteById(memberCardEntity);
        }
    }
}
