package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.*;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.GoodsClassMapper;
import com.liuhao.datasynctask.mapper.GoodssupplierMapper;
import com.liuhao.datasynctask.mapper.ProductClassMapper;
import com.liuhao.datasynctask.mapper.SupplierMapper;
import com.liuhao.datasynctask.service.ProductClassService;
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
public class ProductClassServiceImpl extends ServiceImpl<ProductClassMapper, ProductClassEntity> implements ProductClassService {



    @Autowired
    private ProductClassMapper productClassMapper;

    @Autowired
    private GoodsClassMapper goodsClassMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<ProductClassEntity> productClassEntityList = null;
        try{
            //读取需要同步的数据
            productClassEntityList =  productClassMapper.getData();
            //将读取了的数据，做标志
            for (ProductClassEntity productClassEntity : productClassEntityList) {
                productClassEntity.setSyncFlag("1");
                QueryWrapper<ProductClassEntity> queryWrapper = new QueryWrapper();
                queryWrapper.eq("classid",productClassEntity.getClassid());
                productClassMapper.update(productClassEntity,queryWrapper);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(productClassEntityList==null||productClassEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(productClassEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            ProductClassEntity productClassEntity = JSONObject.parseObject(sourceData, ProductClassEntity.class);
            QueryWrapper<GoodsClassEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",beginHandler.getCompanId());
            queryWrapper.eq("ClassId",productClassEntity.getClassid());
            GoodsClassEntity goodsClassEntity = goodsClassMapper.selectOne(queryWrapper);
            if(goodsClassEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(goodsClassEntity);
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
            ProductClassEntity productClassEntity = JSONObject.parseObject(sourceData, ProductClassEntity.class);
            GoodsClassEntity goodsClassEntity = new GoodsClassEntity();
            //TODO company_id需要另外写
            goodsClassEntity.setCompanyId(beginHandler.getCompanId());
            goodsClassEntity.setId(productClassEntity.getId());
            goodsClassEntity.setClassId(productClassEntity.getClassid());
            goodsClassEntity.setClassName(productClassEntity.getClassname());
            goodsClassEntity.setParentCode(productClassEntity.getParentcode());
            goodsClassEntity.setClassLevel(productClassEntity.getLevel());
            goodsClassEntity.setHasChild(productClassEntity.getHaschild());
            goodsClassEntity.setIsfresh(productClassEntity.getPotflag());
            goodsClassEntity.setTouchVisible(productClassEntity.getStatus());
            goodsClassEntity.setStatus("");//无值，传空
            goodsClassEntity.setPointrate(productClassEntity.getPoints());
            goodsClassEntity.setCreateDate(productClassEntity.getCreatedate());
            goodsClassEntity.setUpdateDate(productClassEntity.getUpdatedate());
            goodsClassEntity.setSyncTime(LocalDateTime.now());
            goodsClassEntity.setSyncFlag(productClassEntity.getSyncFlag());
            goodsClassMapper.insert(goodsClassEntity);
            return JSONObject.toJSONString(productClassEntity);
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
            GoodsClassEntity goodsClassEntity = JSONObject.parseObject(sourceData, GoodsClassEntity.class);
            goodsClassEntity.setSyncTime(LocalDateTime.now());
            QueryWrapper<GoodsClassEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",goodsClassEntity.getCompanyId());
            queryWrapper.eq("ClassId",goodsClassEntity.getClassId());
            goodsClassMapper.update(goodsClassEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            ProductClassEntity productClassEntity = JSONObject.parseObject(sourceData, ProductClassEntity.class);
            productClassEntity.setSyncTime(LocalDateTime.now());
            productClassEntity.setSyncFlag("0");
            QueryWrapper<ProductClassEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("classid",productClassEntity.getClassid());
            productClassMapper.update(productClassEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    @DS("sqlserver")
    public void backSyncFalg() {
        productClassMapper.backSyncFalg();
    }
}
