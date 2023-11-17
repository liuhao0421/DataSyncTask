package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.GoodsEntity;
import com.liuhao.datasynctask.entity.GoodsmulticodeEntity;
import com.liuhao.datasynctask.entity.ProductBarcodeEntity;
import com.liuhao.datasynctask.entity.ProductEntity;
import com.liuhao.datasynctask.service.ProductBarcodeService;
import com.liuhao.datasynctask.service.ProductService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
public class ProBarSyncToGoodsMCGoodsHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
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
                            dataSyncService.updateSourceData(syncedData);
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
                            dataSyncService.updateTargetData(JSONObject.toJSONString(goodsmulticodeEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(productBarcodeEntity));
                        }
                    }
                }else{
                    System.out.println("product_barcode无需要同步的数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            sendMessageServcice.sendText(e.getMessage());
        }
    }
}
