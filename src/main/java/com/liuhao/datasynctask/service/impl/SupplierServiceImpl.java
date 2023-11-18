package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.GoodsmulticodeEntity;
import com.liuhao.datasynctask.entity.GoodssupplierEntity;
import com.liuhao.datasynctask.entity.ProductBarcodeEntity;
import com.liuhao.datasynctask.entity.SupplierEntity;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.GoodsmulticodeMapper;
import com.liuhao.datasynctask.mapper.GoodssupplierMapper;
import com.liuhao.datasynctask.mapper.ProductBarcodeMapper;
import com.liuhao.datasynctask.mapper.SupplierMapper;
import com.liuhao.datasynctask.service.SupplierService;
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
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, SupplierEntity> implements SupplierService {



    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private GoodssupplierMapper goodssupplierMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<SupplierEntity> supplierEntityList = null;
        try{
            //读取需要同步的数据
            supplierEntityList =  supplierMapper.getData();
            //将读取了的数据，做标志
            for (SupplierEntity supplierEntity : supplierEntityList) {
                supplierEntity.setSyncFlag("1");
                supplierMapper.updateById(supplierEntity);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(supplierEntityList==null||supplierEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(supplierEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            SupplierEntity supplierEntity = JSONObject.parseObject(sourceData, SupplierEntity.class);
            QueryWrapper<GoodssupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("supId",supplierEntity.getSupid());
            GoodssupplierEntity goodssupplierEntity = goodssupplierMapper.selectOne(queryWrapper);
            if(goodssupplierEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(goodssupplierEntity);
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
            SupplierEntity supplierEntity = JSONObject.parseObject(sourceData, SupplierEntity.class);
            GoodssupplierEntity goodssupplierEntity = new GoodssupplierEntity();
            //TODO company_id需要另外写
            goodssupplierEntity.setCompanyId(beginHandler.getCompanId());
            goodssupplierEntity.setSupId(supplierEntity.getSupid());
            goodssupplierEntity.setSupName(supplierEntity.getSupname());
            goodssupplierEntity.setAddr(supplierEntity.getAddress());
            goodssupplierEntity.setPhone(supplierEntity.getTel());
            goodssupplierEntity.setFax(supplierEntity.getFax());
            goodssupplierEntity.setContacts(supplierEntity.getContactman());
            goodssupplierEntity.setEmail(supplierEntity.getEmail());
            goodssupplierEntity.setLegalperson(supplierEntity.getChiefman());
            goodssupplierEntity.setBusinessLicence("");//无值，传空
            goodssupplierEntity.setBankName(supplierEntity.getBankname());
            goodssupplierEntity.setBankAccount(supplierEntity.getBankAcctNo());
            goodssupplierEntity.setBankPhone("");//无值，传空
            goodssupplierEntity.setSupplyMode(supplierEntity.getBusinesstype());
            goodssupplierEntity.setSettlementMode(supplierEntity.getSettlementMode());
            goodssupplierEntity.setSettlementDays(supplierEntity.getSettledays());
            goodssupplierEntity.setPayMode(supplierEntity.getPaymethod());
            goodssupplierEntity.setStockType(supplierEntity.getStocktaking());
            goodssupplierEntity.setOrderWeekDay(supplierEntity.getOrderWeekDay());
            goodssupplierEntity.setDeliveryWeekDay(supplierEntity.getSendweekday());
            goodssupplierEntity.setDeliveryDays(supplierEntity.getPredays());
            goodssupplierEntity.setOrderExpireDays(supplierEntity.getLimitdays());
            goodssupplierEntity.setOperId("");//无值，传空
            goodssupplierEntity.setCreateDate(supplierEntity.getCreatedate());
            goodssupplierEntity.setUpdateUser("");//无值，传空
            goodssupplierEntity.setUpdateDate(supplierEntity.getUpdatedate());
            goodssupplierEntity.setStatus(supplierEntity.getStatus());
            goodssupplierEntity.setRemark(supplierEntity.getRemark());
            goodssupplierEntity.setIsStrongRelation(supplierEntity.getPurtype());
            goodssupplierEntity.setSyncTime(LocalDateTime.now());
            goodssupplierEntity.setSyncFlag(supplierEntity.getSyncFlag());
            goodssupplierMapper.insert(goodssupplierEntity);
            return JSONObject.toJSONString(supplierEntity);
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
            GoodssupplierEntity goodssupplierEntity = JSONObject.parseObject(sourceData, GoodssupplierEntity.class);
            goodssupplierEntity.setSyncTime(LocalDateTime.now());
            goodssupplierMapper.updateById(goodssupplierEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            SupplierEntity supplierEntity = JSONObject.parseObject(sourceData, SupplierEntity.class);
            supplierEntity.setSyncTime(LocalDateTime.now());
            supplierEntity.setSyncFlag("0");
            supplierMapper.updateById(supplierEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
