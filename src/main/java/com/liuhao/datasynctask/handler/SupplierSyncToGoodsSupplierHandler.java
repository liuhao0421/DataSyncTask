package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.GoodsmulticodeEntity;
import com.liuhao.datasynctask.entity.GoodssupplierEntity;
import com.liuhao.datasynctask.entity.ProductBarcodeEntity;
import com.liuhao.datasynctask.entity.SupplierEntity;
import com.liuhao.datasynctask.service.ProductBarcodeService;
import com.liuhao.datasynctask.service.SupplierService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
@Slf4j
public class SupplierSyncToGoodsSupplierHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public SupplierService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<SupplierEntity> supplierEntityList = JSONObject.parseArray(sourceData, SupplierEntity.class);
                    for (SupplierEntity supplierEntity : supplierEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(supplierEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(supplierEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(supplierEntity));
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            GoodssupplierEntity goodssupplierEntity = JSONObject.parseObject(result, GoodssupplierEntity.class);
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
                            goodssupplierEntity.setSyncFlag(supplierEntity.getSyncFlag());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(goodssupplierEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(supplierEntity));
                        }
                    }
                }else{
                    log.info("supplier无需要同步的数据！！！！！！！");
                    dataSyncService.backSyncFalg();
                    Thread.sleep(650000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
