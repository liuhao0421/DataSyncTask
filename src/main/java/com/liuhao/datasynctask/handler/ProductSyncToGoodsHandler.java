package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.GoodsEntity;
import com.liuhao.datasynctask.entity.ProductEntity;
import com.liuhao.datasynctask.entity.RedEnvelopeEntity;
import com.liuhao.datasynctask.entity.VCouponListUpEntity;
import com.liuhao.datasynctask.service.ProductService;
import com.liuhao.datasynctask.service.VCouponListUpService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class ProductSyncToGoodsHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public ProductService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<ProductEntity> productEntityList = JSONObject.parseArray(sourceData, ProductEntity.class);
                    for (ProductEntity productEntity : productEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(productEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(productEntity));
                            dataSyncService.updateSourceData(syncedData);
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            GoodsEntity goodsEntity = JSONObject.parseObject(result, GoodsEntity.class);
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
                            goodsEntity.setSyncFlag(productEntity.getSyncFlag());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(goodsEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(productEntity));
                        }
                    }
                }else{
                    log.info("product无需要同步的数据！！！！！！！");
                    dataSyncService.backSyncFalg();
                    Thread.sleep(30000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
