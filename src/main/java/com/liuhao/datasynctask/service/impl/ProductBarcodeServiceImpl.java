package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.GoodsEntity;
import com.liuhao.datasynctask.entity.GoodsmulticodeEntity;
import com.liuhao.datasynctask.entity.ProductBarcodeEntity;
import com.liuhao.datasynctask.entity.ProductEntity;
import com.liuhao.datasynctask.handler.BeginHandler;
import com.liuhao.datasynctask.mapper.GoodsMapper;
import com.liuhao.datasynctask.mapper.GoodsmulticodeMapper;
import com.liuhao.datasynctask.mapper.ProductBarcodeMapper;
import com.liuhao.datasynctask.mapper.ProductMapper;
import com.liuhao.datasynctask.service.ProductBarcodeService;
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
public class ProductBarcodeServiceImpl extends ServiceImpl<ProductBarcodeMapper, ProductBarcodeEntity> implements ProductBarcodeService {



    @Autowired
    private ProductBarcodeMapper productBarcodeMapper;

    @Autowired
    private GoodsmulticodeMapper goodsmulticodeMapper;
    @Autowired
    BeginHandler beginHandler;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<ProductBarcodeEntity> productBarcodeEntityList = null;
        try{
            //读取需要同步的数据
            productBarcodeEntityList =  productBarcodeMapper.getData();
            //将读取了的数据，做标志
            for (ProductBarcodeEntity productBarcodeEntity : productBarcodeEntityList) {
                productBarcodeEntity.setSyncFlag("1");
                QueryWrapper<ProductBarcodeEntity> queryWrapper = new QueryWrapper();
                queryWrapper.eq("proid",productBarcodeEntity.getProid());
                queryWrapper.eq("barcode",productBarcodeEntity.getBarcode());
                productBarcodeMapper.update(productBarcodeEntity,queryWrapper);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(productBarcodeEntityList==null||productBarcodeEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(productBarcodeEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            ProductBarcodeEntity productBarcodeEntity = JSONObject.parseObject(sourceData, ProductBarcodeEntity.class);
            QueryWrapper<GoodsmulticodeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",beginHandler.getCompanId());
            queryWrapper.eq("goodsid",productBarcodeEntity.getProid());
            queryWrapper.eq("barcode",productBarcodeEntity.getBarcode());
            GoodsmulticodeEntity goodsmulticodeEntity = goodsmulticodeMapper.selectOne(queryWrapper);
            if(goodsmulticodeEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(goodsmulticodeEntity);
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
            ProductBarcodeEntity productBarcodeEntity = JSONObject.parseObject(sourceData, ProductBarcodeEntity.class);
            GoodsmulticodeEntity goodsmulticodeEntity = new GoodsmulticodeEntity();
            //TODO company_id需要另外写
            goodsmulticodeEntity.setCompanyId(beginHandler.getCompanId());
            goodsmulticodeEntity.setGoodsid(productBarcodeEntity.getProid());
            goodsmulticodeEntity.setBarcode(productBarcodeEntity.getBarcode());
            goodsmulticodeEntity.setPluno(productBarcodeEntity.getPluno());
            goodsmulticodeEntity.setLabelFormat(productBarcodeEntity.getLabelformat());
            goodsmulticodeEntity.setIsMain(productBarcodeEntity.getMainflag());
            goodsmulticodeEntity.setCreateDate(productBarcodeEntity.getCreatedate());
            goodsmulticodeEntity.setUpdateDate(productBarcodeEntity.getUpdatedate());
            goodsmulticodeEntity.setBarType(productBarcodeEntity.getBarmode());
            goodsmulticodeEntity.setSyncTime(LocalDateTime.now());
            goodsmulticodeEntity.setSyncFlag(productBarcodeEntity.getSyncFlag());
            goodsmulticodeMapper.insert(goodsmulticodeEntity);
            return JSONObject.toJSONString(productBarcodeEntity);
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
            GoodsmulticodeEntity goodsmulticodeEntity = JSONObject.parseObject(sourceData, GoodsmulticodeEntity.class);
            goodsmulticodeEntity.setSyncTime(LocalDateTime.now());
            QueryWrapper<GoodsmulticodeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("company_id",goodsmulticodeEntity.getCompanyId());
            queryWrapper.eq("goodsid",goodsmulticodeEntity.getGoodsid());
            queryWrapper.eq("barcode",goodsmulticodeEntity.getBarcode());
            goodsmulticodeMapper.update(goodsmulticodeEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            ProductBarcodeEntity productBarcodeEntity = JSONObject.parseObject(sourceData, ProductBarcodeEntity.class);
            productBarcodeEntity.setSyncTime(LocalDateTime.now());
            productBarcodeEntity.setSyncFlag("0");
            QueryWrapper<ProductBarcodeEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("proid",productBarcodeEntity.getProid());
            queryWrapper.eq("barcode",productBarcodeEntity.getBarcode());
            productBarcodeMapper.update(productBarcodeEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
