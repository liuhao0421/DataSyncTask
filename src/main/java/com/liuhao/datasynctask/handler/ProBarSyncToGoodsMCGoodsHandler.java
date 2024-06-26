package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.util.PushUtil;
import com.liuhao.datasynctask.entity.GoodsmulticodeEntity;
import com.liuhao.datasynctask.entity.ProductBarcodeEntity;
import com.liuhao.datasynctask.service.ProductBarcodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
@Slf4j
public class ProBarSyncToGoodsMCGoodsHandler {
    
    @Autowired
    public ProductBarcodeService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<ProductBarcodeEntity> productBarcodeEntityList = JSONObject.parseArray(sourceData, ProductBarcodeEntity.class);
                    for (ProductBarcodeEntity productBarcodeEntity : productBarcodeEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(productBarcodeEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(productBarcodeEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(productBarcodeEntity));
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            GoodsmulticodeEntity goodsmulticodeEntity = JSONObject.parseObject(result, GoodsmulticodeEntity.class);
                            goodsmulticodeEntity.setCompanyId(beginHandler.getCompanId());
                            goodsmulticodeEntity.setGoodsid(productBarcodeEntity.getProid());
                            goodsmulticodeEntity.setBarcode(productBarcodeEntity.getBarcode());
                            goodsmulticodeEntity.setPluno(productBarcodeEntity.getPluno());
                            goodsmulticodeEntity.setLabelFormat(productBarcodeEntity.getLabelformat());
                            goodsmulticodeEntity.setIsMain(productBarcodeEntity.getMainflag());
                            goodsmulticodeEntity.setCreateDate(productBarcodeEntity.getCreatedate());
                            goodsmulticodeEntity.setUpdateDate(productBarcodeEntity.getUpdatedate());
                            goodsmulticodeEntity.setBarType(productBarcodeEntity.getBarmode());
                            goodsmulticodeEntity.setSyncFlag(productBarcodeEntity.getSyncFlag());
                            goodsmulticodeEntity.setSyncTime(LocalDateTime.now());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(goodsmulticodeEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(productBarcodeEntity));
                        }
                    }
                }else{
                    log.info("product_barcode无需要同步的数据！！！！！！！");
                    dataSyncService.backSyncFalg();
                    Thread.sleep(350000);

                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            PushUtil.push(beginHandler.getCompanName()+", 数据同步存在异常");
        }
    }
}
