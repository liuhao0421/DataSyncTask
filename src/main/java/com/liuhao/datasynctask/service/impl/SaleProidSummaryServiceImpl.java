package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.PosPayFlowEntity;
import com.liuhao.datasynctask.entity.SaleGoodsSummaryEntity;
import com.liuhao.datasynctask.entity.SalePaymodeEntity;
import com.liuhao.datasynctask.entity.SaleProidSummaryEntity;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.PosPayFlowMapper;
import com.liuhao.datasynctask.mapper.SaleGoodsSummaryMapper;
import com.liuhao.datasynctask.mapper.SalePaymodeMapper;
import com.liuhao.datasynctask.mapper.SaleProidSummaryMapper;
import com.liuhao.datasynctask.service.SaleProidSummaryService;
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
public class SaleProidSummaryServiceImpl extends ServiceImpl<SaleProidSummaryMapper, SaleProidSummaryEntity> implements SaleProidSummaryService {



    @Autowired
    private SaleProidSummaryMapper saleProidSummaryMapper;

    @Autowired
    private SaleGoodsSummaryMapper saleGoodsSummaryMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<SaleProidSummaryEntity> saleProidSummaryEntityList = null;
        try{
            //读取需要同步的数据
            saleProidSummaryEntityList =  saleProidSummaryMapper.getData();
            //将读取了的数据，做标志
            for (SaleProidSummaryEntity saleProidSummaryEntity : saleProidSummaryEntityList) {
                saleProidSummaryEntity.setSyncFlag("1");
                saleProidSummaryMapper.updateById(saleProidSummaryEntity);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(saleProidSummaryEntityList==null||saleProidSummaryEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(saleProidSummaryEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            SaleProidSummaryEntity saleProidSummaryEntity  = JSONObject.parseObject(sourceData, SaleProidSummaryEntity.class);
            QueryWrapper<SaleGoodsSummaryEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("storeId",saleProidSummaryEntity.getBraid());
            queryWrapper.eq("goodsId",saleProidSummaryEntity.getProid());
            queryWrapper.eq("saledate",saleProidSummaryEntity.getSaledate());
            SaleGoodsSummaryEntity saleGoodsSummaryEntity = saleGoodsSummaryMapper.selectOne(queryWrapper);
            if(saleGoodsSummaryEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(saleGoodsSummaryEntity);
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
            SaleProidSummaryEntity saleProidSummaryEntity  = JSONObject.parseObject(sourceData, SaleProidSummaryEntity.class);
            SaleGoodsSummaryEntity saleGoodsSummaryEntity = new SaleGoodsSummaryEntity();
            //TODO company_id需要另外写
            saleGoodsSummaryEntity.setCompanyId(beginHandler.getCompanId());
            saleGoodsSummaryEntity.setStoreId(saleProidSummaryEntity.getBraid());
            saleGoodsSummaryEntity.setSaledate(saleProidSummaryEntity.getSaledate());
            saleGoodsSummaryEntity.setGoodsId(saleProidSummaryEntity.getProid());
            saleGoodsSummaryEntity.setSupid(saleProidSummaryEntity.getSupid());
            saleGoodsSummaryEntity.setRetType("");//无值，传空
            saleGoodsSummaryEntity.setRetRate(saleProidSummaryEntity.getReturnrat());
            saleGoodsSummaryEntity.setSaleQty(saleProidSummaryEntity.getSaleqty());
            saleGoodsSummaryEntity.setSaleAmt(saleProidSummaryEntity.getSaleamt());
            saleGoodsSummaryEntity.setSalePrice("");//无值，传空
            saleGoodsSummaryEntity.setAvgSalePrice(saleProidSummaryEntity.getAvgsaleprice());
            saleGoodsSummaryEntity.setSaleCostPrice(saleProidSummaryEntity.getSalecostprice());
            saleGoodsSummaryEntity.setSaleCostAmt(saleProidSummaryEntity.getSalecostamt());
            saleGoodsSummaryEntity.setSaleDisAmt(saleProidSummaryEntity.getSaledisamt());
            saleGoodsSummaryEntity.setSaleProfit(saleProidSummaryEntity.getSaleprofit());
            saleGoodsSummaryEntity.setPromtQty(saleProidSummaryEntity.getPmtqty());
            saleGoodsSummaryEntity.setPromtAmt(saleProidSummaryEntity.getPmtamt());
            saleGoodsSummaryEntity.setPromtCostAmt(saleProidSummaryEntity.getPmtcostamt());
            saleGoodsSummaryEntity.setPromtDisAmt(saleProidSummaryEntity.getPmtdisamt());
            saleGoodsSummaryEntity.setPromtProfit(saleProidSummaryEntity.getPmtprofit());
            saleGoodsSummaryEntity.setVipQty(saleProidSummaryEntity.getMemqty());
            saleGoodsSummaryEntity.setVipAmt(saleProidSummaryEntity.getMemamt());
            saleGoodsSummaryEntity.setVipCostAmt(saleProidSummaryEntity.getMemcostamt());
            saleGoodsSummaryEntity.setVipDisAmt(saleProidSummaryEntity.getMemdisamt());
            saleGoodsSummaryEntity.setVipProfit(saleProidSummaryEntity.getMemprofit());
            saleGoodsSummaryEntity.setRetQty("");//无值，传空
            saleGoodsSummaryEntity.setRetAmt("");//无值，传空
            saleGoodsSummaryEntity.setSyncTime(LocalDateTime.now());
            saleGoodsSummaryEntity.setSyncFlag(saleProidSummaryEntity.getSyncFlag());
            saleGoodsSummaryMapper.insert(saleGoodsSummaryEntity);
            return JSONObject.toJSONString(saleProidSummaryEntity);
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
            SaleGoodsSummaryEntity saleGoodsSummaryEntity = JSONObject.parseObject(sourceData, SaleGoodsSummaryEntity.class);
            saleGoodsSummaryEntity.setSyncTime(LocalDateTime.now());
            saleGoodsSummaryMapper.updateById(saleGoodsSummaryEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            SaleProidSummaryEntity saleProidSummaryEntity = JSONObject.parseObject(sourceData, SaleProidSummaryEntity.class);
            saleProidSummaryEntity.setSyncTime(LocalDateTime.now());
            saleProidSummaryEntity.setSyncFlag("0");
            saleProidSummaryMapper.updateById(saleProidSummaryEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
