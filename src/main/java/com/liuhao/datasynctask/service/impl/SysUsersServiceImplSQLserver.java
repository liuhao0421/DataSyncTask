package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.*;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.SaleGoodsSummaryMapper;
import com.liuhao.datasynctask.mapper.SaleProidSummaryMapper;
import com.liuhao.datasynctask.mapper.SysUsersMapper;
import com.liuhao.datasynctask.mapper.SysUsersMapperSQLserver;
import com.liuhao.datasynctask.service.SysUsersServiceSQLserver;
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
public class SysUsersServiceImplSQLserver extends ServiceImpl<SysUsersMapperSQLserver, SysUsersEntitySQLserver> implements SysUsersServiceSQLserver {



    @Autowired
    private SysUsersMapperSQLserver sysUsersMapperSQLserver;

    @Autowired
    private SysUsersMapper sysUsersMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<SysUsersEntitySQLserver> sysUsersEntitySQLserverList = null;
        try{
            //读取需要同步的数据
            sysUsersEntitySQLserverList =  sysUsersMapperSQLserver.getData();
            //将读取了的数据，做标志
            for (SysUsersEntitySQLserver sysUsersEntitySQLserver : sysUsersEntitySQLserverList) {
                sysUsersEntitySQLserver.setSyncFlag("1");
                QueryWrapper<SysUsersEntitySQLserver> queryWrapper = new QueryWrapper();
                queryWrapper.eq("UserId",sysUsersEntitySQLserver.getUserId());
                sysUsersMapperSQLserver.update(sysUsersEntitySQLserver,queryWrapper);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(sysUsersEntitySQLserverList==null||sysUsersEntitySQLserverList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(sysUsersEntitySQLserverList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            SysUsersEntitySQLserver sysUsersEntitySQLserver = JSONObject.parseObject(sourceData, SysUsersEntitySQLserver.class);
            QueryWrapper<SysUsersEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",beginHandler.getCompanId());
            queryWrapper.eq("UserId",sysUsersEntitySQLserver.getUserId());
            SysUsersEntity sysUsersEntity = sysUsersMapper.selectOne(queryWrapper);
            if(sysUsersEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(sysUsersEntity);
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
            SysUsersEntitySQLserver sysUsersEntitySQLserver  = JSONObject.parseObject(sourceData, SysUsersEntitySQLserver.class);
            SysUsersEntity sysUsersEntity = new SysUsersEntity();
            //TODO company_id需要另外写
            sysUsersEntity.setCompanyId(beginHandler.getCompanId());
            sysUsersEntity.setStoreId(sysUsersEntitySQLserver.getStoreId());
            sysUsersEntity.setUserId(sysUsersEntitySQLserver.getUserId());
            sysUsersEntity.setUserName(sysUsersEntitySQLserver.getUserName());
            sysUsersEntity.setPwd(sysUsersEntitySQLserver.getPwd());
            sysUsersEntity.setStatus(sysUsersEntitySQLserver.getStatus());
            sysUsersEntity.setSex(sysUsersEntitySQLserver.getSex());
            sysUsersEntity.setBirthday(sysUsersEntitySQLserver.getBirthday());
            sysUsersEntity.setAddr(sysUsersEntitySQLserver.getAddr());
            sysUsersEntity.setTel(sysUsersEntitySQLserver.getTel());
            sysUsersEntity.setMobile(sysUsersEntitySQLserver.getMobile());
            sysUsersEntity.setDiscountRate(sysUsersEntitySQLserver.getDiscountRate());
            sysUsersEntity.setChangePriceRate(sysUsersEntitySQLserver.getChangePriceRate());
            sysUsersEntity.setRemark(sysUsersEntitySQLserver.getRemark());
            sysUsersEntity.setChgpwddate(sysUsersEntitySQLserver.getChgpwddate());
            sysUsersEntity.setLeavedate(sysUsersEntitySQLserver.getLeavedate());
            sysUsersEntity.setCreateDate(sysUsersEntitySQLserver.getCreateDate());
            sysUsersEntity.setUpdateDate(sysUsersEntitySQLserver.getUpdateDate());
            sysUsersEntity.setOutCashierid(sysUsersEntitySQLserver.getOutCashierid());
            sysUsersEntity.setCardsalerate(sysUsersEntitySQLserver.getCardsalerate());
            sysUsersEntity.setSyncTime(LocalDateTime.now());
            sysUsersEntity.setSyncFlag(sysUsersEntitySQLserver.getSyncFlag());
            sysUsersMapper.insert(sysUsersEntity);
            return JSONObject.toJSONString(sysUsersEntitySQLserver);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    //将修改的数据同步至目标表
    @Override
    @DS("mysql")
    public void updateTargetData(String sourceData) {
        try{
            SysUsersEntity sysUsersEntity = JSONObject.parseObject(sourceData, SysUsersEntity.class);
            sysUsersEntity.setSyncTime(LocalDateTime.now());
            QueryWrapper<SysUsersEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",sysUsersEntity.getCompanyId());
            queryWrapper.eq("UserId",sysUsersEntity.getUserId());
            sysUsersMapper.update(sysUsersEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            SysUsersEntitySQLserver sysUsersEntitySQLserver = JSONObject.parseObject(sourceData, SysUsersEntitySQLserver.class);
            sysUsersEntitySQLserver.setSyncTime(LocalDateTime.now());
            sysUsersEntitySQLserver.setSyncFlag("0");
            QueryWrapper<SysUsersEntitySQLserver> queryWrapper = new QueryWrapper();
            queryWrapper.eq("UserId",sysUsersEntitySQLserver.getUserId());
            sysUsersMapperSQLserver.update(sysUsersEntitySQLserver,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
