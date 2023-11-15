package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.mapper.MemberCardMapper;
import com.liuhao.datasynctask.service.MemberCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCardEntity> implements MemberCardService {

    @Autowired
    private MemberCardMapper memberCardMapper;

    //获取数据源修改的数据
    @Override
    @DS("mysql")
    public String getUpdateDataFromSource() {
        List<MemberCardEntity> memberCardEntityList = null;
        try{
            //读取需要同步的数据
            memberCardEntityList =  memberCardMapper.getUpdateData();
            //将读取了的数据，做标志
            for (MemberCardEntity memberCardEntity : memberCardEntityList) {
                memberCardEntity.setSyncFlag("1");
                memberCardMapper.updateById(memberCardEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
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
    public String getDataFromSource() {
        List<MemberCardEntity> memberCardEntityList = null;
        try{
            //读取需要同步的数据
            memberCardEntityList =  memberCardMapper.getData();
            //将读取了的数据，做标志
            for (MemberCardEntity memberCardEntity : memberCardEntityList) {
                memberCardEntity.setSyncFlag("1");
                memberCardMapper.updateById(memberCardEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(memberCardEntityList==null||memberCardEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(memberCardEntityList);
        }
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }
}
