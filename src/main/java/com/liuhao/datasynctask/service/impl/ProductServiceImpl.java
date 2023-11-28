package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.GoodsEntity;
import com.liuhao.datasynctask.entity.ProductEntity;
import com.liuhao.datasynctask.entity.RedEnvelopeEntity;
import com.liuhao.datasynctask.entity.VCouponListUpEntity;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.GoodsMapper;
import com.liuhao.datasynctask.mapper.ProductMapper;
import com.liuhao.datasynctask.mapper.RedEnvelopeMapper;
import com.liuhao.datasynctask.mapper.VCouponListUpMapper;
import com.liuhao.datasynctask.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {



    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<ProductEntity> productEntityList = null;
        try{
            //读取需要同步的数据
            productEntityList =  productMapper.getData();
            //将读取了的数据，做标志
            for (ProductEntity productEntity : productEntityList) {
                productEntity.setSyncFlag("1");
                QueryWrapper<ProductEntity> queryWrapper = new QueryWrapper();
                queryWrapper.eq("proid",productEntity.getProid());
                productMapper.update(productEntity,queryWrapper);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(productEntityList==null||productEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(productEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            ProductEntity productEntity = JSONObject.parseObject(sourceData, ProductEntity.class);
            QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",beginHandler.getCompanId());
            queryWrapper.eq("goodsid",productEntity.getProid());
            GoodsEntity goodsEntity = goodsMapper.selectOne(queryWrapper);
            if(goodsEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(goodsEntity);
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
            ProductEntity productEntity = JSONObject.parseObject(sourceData, ProductEntity.class);
            GoodsEntity goodsEntity = new GoodsEntity();
            //TODO company_id需要另外写
            goodsEntity.setCompanyId(beginHandler.getCompanId());
            goodsEntity.setGoodsid(productEntity.getProid());
            goodsEntity.setBarcode(productEntity.getBarcode());
            goodsEntity.setGoodsname(productEntity.getProname());
            goodsEntity.setUnitno(productEntity.getMeasureid());
            goodsEntity.setInprice(productEntity.getTaxprice());
            goodsEntity.setSaleprice(productEntity.getNormalprice());
            goodsEntity.setVipPrice(productEntity.getMemberprice());
            goodsEntity.setVipPoint(productEntity.getCardpoint());
            goodsEntity.setSpec(productEntity.getSpec());
            goodsEntity.setArea(productEntity.getArea());
            goodsEntity.setGrade(productEntity.getGrade());
            goodsEntity.setClassid(productEntity.getClassid());
            goodsEntity.setBrandid(productEntity.getBrandid());
            goodsEntity.setSupid(productEntity.getSupid());
            goodsEntity.setIntax(productEntity.getIntax());
            goodsEntity.setSaletax(productEntity.getSaletax());
            goodsEntity.setHelpCode(productEntity.getHelpcode());
            goodsEntity.setBoxrate(productEntity.getPacketqty());
            goodsEntity.setMinOrderQty(productEntity.getMinorderqty());
            goodsEntity.setBarType(productEntity.getBarmode());
            goodsEntity.setProType(productEntity.getProType());
            goodsEntity.setSupplyMode(productEntity.getProflag());
            goodsEntity.setCanScalechgPrice(productEntity.getUdf3());
            goodsEntity.setVipDisType(productEntity.getPotflag());
            goodsEntity.setMinDisrate(productEntity.getMinDisrate());
            goodsEntity.setPrintMode(productEntity.getWeightflag());
            goodsEntity.setShelfDay(productEntity.getWarrantydays());
            goodsEntity.setSeasonal(productEntity.getSeasonal());
            goodsEntity.setRetType(productEntity.getReturntype());
            goodsEntity.setRetRate(productEntity.getReturnrat());
            goodsEntity.setAlarmDays(productEntity.getAlarmdays());
            goodsEntity.setCpsType(productEntity.getCpsrate());
            goodsEntity.setStockType(productEntity.getStocktaking());
            goodsEntity.setOperId(productEntity.getOperatorid());
            goodsEntity.setCreatedate(productEntity.getCreatedate());
            goodsEntity.setLastUpdateUser(productEntity.getLastUpdateUser());
            goodsEntity.setUpdatedate(productEntity.getUpdatedate());
            goodsEntity.setStopdate(productEntity.getStopdate());
            goodsEntity.setStatus(productEntity.getStatus());
            goodsEntity.setIngredient(productEntity.getMaterial());
            goodsEntity.setSaleMethod(productEntity.getSaleMethod());
            goodsEntity.setSyncTime(productEntity.getSyncTime());
            goodsEntity.setSyncFlag(productEntity.getSyncFlag());
            goodsMapper.insert(goodsEntity);
            return JSONObject.toJSONString(productEntity);
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
            GoodsEntity goodsEntity = JSONObject.parseObject(sourceData, GoodsEntity.class);
            goodsEntity.setSyncTime(LocalDateTime.now());
            QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("company_id",goodsEntity.getCompanyId());
            queryWrapper.eq("goodsid",goodsEntity.getGoodsid());
            goodsMapper.update(goodsEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            ProductEntity productEntity = JSONObject.parseObject(sourceData, ProductEntity.class);
            productEntity.setSyncTime(LocalDateTime.now());
            productEntity.setSyncFlag("0");
            QueryWrapper<ProductEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("proid",productEntity.getProid());
            productMapper.update(productEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    @DS("sqlserver")
    public void backSyncFalg() {
        productMapper.backSyncFalg();
    }
}
