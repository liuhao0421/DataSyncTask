package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.MemberAmtEntity;
import com.liuhao.datasynctask.entity.MemberAmtEntity;
import com.liuhao.datasynctask.mapper.MemberAmtMapper;
import com.liuhao.datasynctask.mapper.MemberAmtMapper;
import com.liuhao.datasynctask.service.MemberAmtService;
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
 * @since 2023-11-05
 */
@Service
public class MemberAmtServiceImpl extends ServiceImpl<MemberAmtMapper, MemberAmtEntity> implements MemberAmtService {

    @Autowired
    private MemberAmtMapper memberAmtMapper;
    //获取数据源新增的数据
    @Override
    @DS("mysql")
    public String getDataFromSource() {
        List<MemberAmtEntity> memberAmtEntityList = null;
        try{
            //读取需要同步的数据
            memberAmtEntityList =  memberAmtMapper.getData();
            //将读取了的数据，做标志
            for (MemberAmtEntity memberAmtEntity : memberAmtEntityList) {
                memberAmtEntity.setSyncFlag("1");
                memberAmtMapper.updateById(memberAmtEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(memberAmtEntityList==null||memberAmtEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(memberAmtEntityList);
        }
    }

    //将新增的数据同步至目标表
    @Override
    @DS("sqlserver")
    public String pushDataToTarget(String sourceData) {
        try{
            MemberAmtEntity memberAmtEntity = JSONObject.parseObject(sourceData, MemberAmtEntity.class);
            memberAmtEntity.setSyncTime(LocalDateTime.now());
            memberAmtMapper.insert(memberAmtEntity);
            return JSONObject.toJSONString(memberAmtEntity);
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
            MemberAmtEntity memberAmtEntity = JSONObject.parseObject(sourceData, MemberAmtEntity.class);
            memberAmtEntity.setSyncTime(LocalDateTime.now());
            memberAmtEntity.setSyncFlag("0");
            memberAmtMapper.updateById(memberAmtEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
