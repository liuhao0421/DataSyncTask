package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.GoodsClassEntity;
import com.liuhao.datasynctask.entity.PosGoodsFlowEntity;
import com.liuhao.datasynctask.entity.ProductClassEntity;
import com.liuhao.datasynctask.entity.SaleDailyEntity;
import com.liuhao.datasynctask.mapper.GoodsClassMapper;
import com.liuhao.datasynctask.mapper.PosGoodsFlowMapper;
import com.liuhao.datasynctask.mapper.ProductClassMapper;
import com.liuhao.datasynctask.mapper.SaleDailyMapper;
import com.liuhao.datasynctask.service.SaleDailyService;
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
public class SaleDailyServiceImpl extends ServiceImpl<SaleDailyMapper, SaleDailyEntity> implements SaleDailyService {



    @Autowired
    private SaleDailyMapper saleDailyMapper;

    @Autowired
    private PosGoodsFlowMapper posGoodsFlowMapper;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<SaleDailyEntity> saleDailyEntityList = null;
        try{
            //读取需要同步的数据
            saleDailyEntityList =  saleDailyMapper.getData();
            //将读取了的数据，做标志
            for (SaleDailyEntity saleDailyEntity : saleDailyEntityList) {
                saleDailyEntity.setSyncFlag("1");
                saleDailyMapper.updateById(saleDailyEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(saleDailyEntityList==null||saleDailyEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(saleDailyEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            SaleDailyEntity saleDailyEntity = JSONObject.parseObject(sourceData, SaleDailyEntity.class);
            QueryWrapper<PosGoodsFlowEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("storeId",saleDailyEntity.getBraid());
            queryWrapper.eq("saleId",saleDailyEntity.getSaleid());
            queryWrapper.eq("batchid",saleDailyEntity.getBatch());
            queryWrapper.eq("goodsId",saleDailyEntity.getProid());
            PosGoodsFlowEntity posGoodsFlowEntity = posGoodsFlowMapper.selectOne(queryWrapper);
            if(posGoodsFlowEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(posGoodsFlowEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //将新增的数据同步至目标表
    @Override
    @DS("mysql")
    public String pushDataToTarget(String sourceData) {
        try{
            SaleDailyEntity saleDailyEntity = JSONObject.parseObject(sourceData, SaleDailyEntity.class);
            PosGoodsFlowEntity posGoodsFlowEntity = new PosGoodsFlowEntity();
            //TODO company_id需要另外写
            posGoodsFlowEntity.setCompanyId("");
            posGoodsFlowEntity.setStoreId(saleDailyEntity.getBraid());
            posGoodsFlowEntity.setSaleId(saleDailyEntity.getSaleid());
            posGoodsFlowEntity.setSaleType(saleDailyEntity.getSaletype());
            posGoodsFlowEntity.setSaleDate(saleDailyEntity.getSaledate());
            posGoodsFlowEntity.setInputDate(saleDailyEntity.getInputdate());
            posGoodsFlowEntity.setUploadDate(saleDailyEntity.getUploaddate());
            posGoodsFlowEntity.setNetStatus(saleDailyEntity.getSendflag());
            posGoodsFlowEntity.setPosno(saleDailyEntity.getPosno());
            posGoodsFlowEntity.setCashierId(saleDailyEntity.getSalerid());
            posGoodsFlowEntity.setBatchid(saleDailyEntity.getBatch());
            posGoodsFlowEntity.setGoodsId(saleDailyEntity.getProid());
            posGoodsFlowEntity.setBarcode(saleDailyEntity.getBarcode());
            posGoodsFlowEntity.setInputBar(saleDailyEntity.getBarcodeBulk());
            posGoodsFlowEntity.setPriceQtyFlag(saleDailyEntity.getInvoiceId());
            posGoodsFlowEntity.setRowid(saleDailyEntity.getBatchrowid());
            posGoodsFlowEntity.setSaleQty(saleDailyEntity.getSaleqty());
            posGoodsFlowEntity.setSaleAmt(saleDailyEntity.getSaleamt());
            posGoodsFlowEntity.setSaleDisAmt(saleDailyEntity.getSaledisamt());
            posGoodsFlowEntity.setInPrice(saleDailyEntity.getLastcostPrice());
            posGoodsFlowEntity.setCostPrice(saleDailyEntity.getCostPrice());
            posGoodsFlowEntity.setSalePrice(saleDailyEntity.getNormalprice());
            posGoodsFlowEntity.setVipPrice(saleDailyEntity.getNcostprice());
            posGoodsFlowEntity.setCurPoint(saleDailyEntity.getCurPrice());
            posGoodsFlowEntity.setCurPoint(saleDailyEntity.getPoints1());
            posGoodsFlowEntity.setClassid(saleDailyEntity.getClassid());
            posGoodsFlowEntity.setBrandid(saleDailyEntity.getBrandid());
            posGoodsFlowEntity.setSupid(saleDailyEntity.getSupid());
            posGoodsFlowEntity.setSupplyMode(saleDailyEntity.getProducttype());
            posGoodsFlowEntity.setStocktype(saleDailyEntity.getStockflag());
            posGoodsFlowEntity.setRetType("");//无值，传空
            posGoodsFlowEntity.setRetRate(saleDailyEntity.getReturnrat());
            posGoodsFlowEntity.setVipPoint(saleDailyEntity.getPoints());
            posGoodsFlowEntity.setMemId(saleDailyEntity.getMemcardno());
            posGoodsFlowEntity.setPromtNo1(saleDailyEntity.getProductpmtplanno());
            posGoodsFlowEntity.setPromtNo2(saleDailyEntity.getBrandclasspmtplanno());
            posGoodsFlowEntity.setPromtNo3(saleDailyEntity.getTranspmtplanno());
            posGoodsFlowEntity.setPromtType(saleDailyEntity.getDonatetype());
            posGoodsFlowEntity.setPromtQty(saleDailyEntity.getPmtqty());
            posGoodsFlowEntity.setPromtDisType(saleDailyEntity.getDisExchange());
            posGoodsFlowEntity.setPromtSaleOnSale(saleDailyEntity.getOverlap());
            posGoodsFlowEntity.setPromtIsPresent(saleDailyEntity.getPresent());
            posGoodsFlowEntity.setPromtIsInPromt(saleDailyEntity.getIsinpromt());
            posGoodsFlowEntity.setPromtRowId(saleDailyEntity.getPmtrowid());
            posGoodsFlowEntity.setRetSaleId(saleDailyEntity.getRetsaleid());
            posGoodsFlowEntity.setRetAccId(saleDailyEntity.getAccreditReturn());
            posGoodsFlowEntity.setDisAmtAccId(saleDailyEntity.getAccreditDisamt());
            posGoodsFlowEntity.setSyncTime(LocalDateTime.now());
            posGoodsFlowEntity.setSyncFlag(saleDailyEntity.getSyncFlag());
            posGoodsFlowMapper.insert(posGoodsFlowEntity);
            return JSONObject.toJSONString(saleDailyEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //将修改的数据同步至目标表
    @Override
    @DS("mysql")
    public void updateTargetData(String sourceData) {
        try{
            PosGoodsFlowEntity posGoodsFlowEntity = JSONObject.parseObject(sourceData, PosGoodsFlowEntity.class);
            posGoodsFlowEntity.setSyncTime(LocalDateTime.now());
            posGoodsFlowMapper.updateById(posGoodsFlowEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            SaleDailyEntity saleDailyEntity = JSONObject.parseObject(sourceData, SaleDailyEntity.class);
            saleDailyEntity.setSyncTime(LocalDateTime.now());
            saleDailyEntity.setSyncFlag("0");
            saleDailyMapper.updateById(saleDailyEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
