package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.GoodsClassEntity;
import com.liuhao.datasynctask.entity.GoodssupplierEntity;
import com.liuhao.datasynctask.entity.ProductClassEntity;
import com.liuhao.datasynctask.entity.SupplierEntity;
import com.liuhao.datasynctask.service.ProductClassService;
import com.liuhao.datasynctask.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
public class ProClassToGoodsClassHandler {
    @Autowired
    public ProductClassService dataSyncService;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<ProductClassEntity> productClassEntityList = JSONObject.parseArray(sourceData, ProductClassEntity.class);
                    for (ProductClassEntity productClassEntity : productClassEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(productClassEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(productClassEntity));
                            dataSyncService.updateSourceData(syncedData);
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            GoodsClassEntity goodsClassEntity = JSONObject.parseObject(result, GoodsClassEntity.class);
                            //TODO company_id需要另外写
                            goodsClassEntity.setCompanyId("");
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
                            goodsClassEntity.setSyncFlag(productClassEntity.getSyncFlag());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(goodsClassEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(productClassEntity));
                        }
                    }
                }else{
                    System.out.println("Product_Class无需要同步的数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
