package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.liuhao.datasynctask.mapper.MemberPointMapper;
import com.liuhao.datasynctask.mapper.MemberPointMapper;
import com.liuhao.datasynctask.service.MemberPointService;
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
 * @since 2023-11-15
 */
@Service
public class MemberPointServiceImpl extends ServiceImpl<MemberPointMapper, MemberPointEntity> implements MemberPointService {

    @Autowired
    private MemberPointMapper memberPointMapper;
    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<MemberPointEntity> memberPointEntityList = null;
        try{
            //读取需要同步的数据
            memberPointEntityList =  memberPointMapper.getData();
            //将读取了的数据，做标志
            for (MemberPointEntity memberPointEntity : memberPointEntityList) {
                memberPointEntity.setSyncFlag("1");
                memberPointMapper.updateById(memberPointEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(memberPointEntityList==null||memberPointEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(memberPointEntityList);
        }
    }


    //将新增的数据同步至目标表
    @Override
    @DS("mysql")
    public String pushDataToTarget(String sourceData) {
        try{
            MemberPointEntity memberPointEntity = JSONObject.parseObject(sourceData, MemberPointEntity.class);
            memberPointEntity.setSyncTime(LocalDateTime.now());
            memberPointMapper.insert(memberPointEntity);
            return JSONObject.toJSONString(memberPointEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            MemberPointEntity memberPointEntity = JSONObject.parseObject(sourceData, MemberPointEntity.class);
            memberPointEntity.setSyncTime(LocalDateTime.now());
            memberPointEntity.setSyncFlag("0");
            memberPointMapper.updateById(memberPointEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
