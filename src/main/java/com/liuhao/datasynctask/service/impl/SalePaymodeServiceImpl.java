package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.PosGoodsFlowEntity;
import com.liuhao.datasynctask.entity.PosPayFlowEntity;
import com.liuhao.datasynctask.entity.SaleDailyEntity;
import com.liuhao.datasynctask.entity.SalePaymodeEntity;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.PosGoodsFlowMapper;
import com.liuhao.datasynctask.mapper.PosPayFlowMapper;
import com.liuhao.datasynctask.mapper.SaleDailyMapper;
import com.liuhao.datasynctask.mapper.SalePaymodeMapper;
import com.liuhao.datasynctask.service.SalePaymodeService;
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
public class SalePaymodeServiceImpl extends ServiceImpl<SalePaymodeMapper, SalePaymodeEntity> implements SalePaymodeService {



    @Autowired
    private SalePaymodeMapper salePaymodeMapper;

    @Autowired
    private PosPayFlowMapper posPayFlowMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<SalePaymodeEntity> salePaymodeEntityList = null;
        try{
            //读取需要同步的数据
            salePaymodeEntityList =  salePaymodeMapper.getData();
            //将读取了的数据，做标志
            for (SalePaymodeEntity salePaymodeEntity : salePaymodeEntityList) {
                salePaymodeEntity.setSyncFlag("1");
                salePaymodeMapper.updateById(salePaymodeEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(salePaymodeEntityList==null||salePaymodeEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(salePaymodeEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            SalePaymodeEntity salePaymodeEntity = JSONObject.parseObject(sourceData, SalePaymodeEntity.class);
            QueryWrapper<PosPayFlowEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("storeid",salePaymodeEntity.getBraid());
            queryWrapper.eq("saleId",salePaymodeEntity.getSaleid());
            PosPayFlowEntity posPayFlowEntity = posPayFlowMapper.selectOne(queryWrapper);
            if(posPayFlowEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(posPayFlowEntity);
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
            SalePaymodeEntity salePaymodeEntity = JSONObject.parseObject(sourceData, SalePaymodeEntity.class);
            PosPayFlowEntity posPayFlowEntity = new PosPayFlowEntity();
            //TODO company_id需要另外写
            posPayFlowEntity.setCompanyId(beginHandler.getCompanId());
            posPayFlowEntity.setStoreid(salePaymodeEntity.getBraid());
            posPayFlowEntity.setSaleId(salePaymodeEntity.getSaleid());
            posPayFlowEntity.setSaleType(salePaymodeEntity.getSaletype());
            posPayFlowEntity.setSaleDate(salePaymodeEntity.getSaledate());
            posPayFlowEntity.setInputDate(salePaymodeEntity.getInputdate());
            posPayFlowEntity.setUploadDate(salePaymodeEntity.getUploaddate());
            posPayFlowEntity.setNetStatus(salePaymodeEntity.getSendflag());
            posPayFlowEntity.setPosno(salePaymodeEntity.getPosno());
            posPayFlowEntity.setCashierId(salePaymodeEntity.getSalerid());
            posPayFlowEntity.setMemId("");//无值，传空
            posPayFlowEntity.setSaleAmt(salePaymodeEntity.getSaleamt());
            posPayFlowEntity.setDisAmt(salePaymodeEntity.getDisamt());
            posPayFlowEntity.setPayAmt(salePaymodeEntity.getPayamt());
            posPayFlowEntity.setRoundAmt(salePaymodeEntity.getPaydibs());
            posPayFlowEntity.setCashAmt(salePaymodeEntity.getCash());
            posPayFlowEntity.setBankAmt(salePaymodeEntity.getCreditcard());
            posPayFlowEntity.setVipAmt(salePaymodeEntity.getMempay());
            posPayFlowEntity.setCouponAmt(salePaymodeEntity.getShoppingticket());
            posPayFlowEntity.setPointAmt(salePaymodeEntity.getPoints());
            posPayFlowEntity.setAliPay(salePaymodeEntity.getAlipay());
            posPayFlowEntity.setWechatPay(salePaymodeEntity.getWxpay());
            posPayFlowEntity.setBestPay(salePaymodeEntity.getBestpay());
            posPayFlowEntity.setOtherAmt(salePaymodeEntity.getOtheramt());
            posPayFlowEntity.setOtherMemo(salePaymodeEntity.getOthermemo());
            posPayFlowEntity.setRecCash(salePaymodeEntity.getReccash());
            posPayFlowEntity.setRetCash(salePaymodeEntity.getRetamt());
            posPayFlowEntity.setCardClearAmt(salePaymodeEntity.getClearamt());
            posPayFlowEntity.setCardDisAmt(salePaymodeEntity.getIccardDisamt());
            posPayFlowEntity.setVipBalance(salePaymodeEntity.getMembalance());
            posPayFlowEntity.setOutTradeNo(salePaymodeEntity.getPayorderno());
            posPayFlowEntity.setSyncTime(LocalDateTime.now());
            posPayFlowEntity.setSyncFlag(salePaymodeEntity.getSyncFlag());
            posPayFlowMapper.insert(posPayFlowEntity);
            return JSONObject.toJSONString(salePaymodeEntity);
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
            PosPayFlowEntity posPayFlowEntity = JSONObject.parseObject(sourceData, PosPayFlowEntity.class);
            posPayFlowEntity.setSyncTime(LocalDateTime.now());
            posPayFlowMapper.updateById(posPayFlowEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            SalePaymodeEntity salePaymodeEntity = JSONObject.parseObject(sourceData, SalePaymodeEntity.class);
            salePaymodeEntity.setSyncTime(LocalDateTime.now());
            salePaymodeEntity.setSyncFlag("0");
            salePaymodeMapper.updateById(salePaymodeEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
