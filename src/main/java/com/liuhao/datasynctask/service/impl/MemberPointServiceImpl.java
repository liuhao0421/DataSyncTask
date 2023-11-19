package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.GoodsClassEntity;
import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.liuhao.datasynctask.entity.ProductClassEntity;
import com.liuhao.datasynctask.mapper.MemberPointMapper;
import com.liuhao.datasynctask.mapper.MemberPointMapper;
import com.liuhao.datasynctask.service.MemberPointService;
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
                QueryWrapper<MemberPointEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("company_id",memberPointEntity.getCompanyId());
                queryWrapper.eq("point_id",memberPointEntity.getPointId());
                queryWrapper.eq("braid",memberPointEntity.getBraid());
                memberPointEntity.setSyncFlag("1");
                memberPointMapper.update(memberPointEntity,queryWrapper);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(memberPointEntityList==null||memberPointEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(memberPointEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            MemberPointEntity memberPointEntity = JSONObject.parseObject(sourceData, MemberPointEntity.class);
            QueryWrapper<MemberPointEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",memberPointEntity.getCompanyId());
            queryWrapper.eq("point_id",memberPointEntity.getPointId());
            queryWrapper.eq("braid",memberPointEntity.getBraid());
            MemberPointEntity memberPointEntityResult = memberPointMapper.selectOne(queryWrapper);
            if(memberPointEntityResult==null){
                return null;
            }else{
                return JSONObject.toJSONString(memberPointEntityResult);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
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
            log.error(e.getMessage());
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
            QueryWrapper<MemberPointEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",memberPointEntity.getCompanyId());
            queryWrapper.eq("point_id",memberPointEntity.getPointId());
            queryWrapper.eq("braid",memberPointEntity.getBraid());
            memberPointMapper.update(memberPointEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
