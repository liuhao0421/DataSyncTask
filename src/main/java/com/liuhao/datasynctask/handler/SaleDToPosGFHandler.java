package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.GoodsClassEntity;
import com.liuhao.datasynctask.entity.PosGoodsFlowEntity;
import com.liuhao.datasynctask.entity.ProductClassEntity;
import com.liuhao.datasynctask.entity.SaleDailyEntity;
import com.liuhao.datasynctask.service.ProductClassService;
import com.liuhao.datasynctask.service.SaleDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
public class SaleDToPosGFHandler {
    @Autowired
    public SaleDailyService dataSyncService;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<SaleDailyEntity> saleDailyEntityList = JSONObject.parseArray(sourceData, SaleDailyEntity.class);
                    for (SaleDailyEntity saleDailyEntity : saleDailyEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(saleDailyEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(saleDailyEntity));
                            dataSyncService.updateSourceData(syncedData);
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            PosGoodsFlowEntity posGoodsFlowEntity = JSONObject.parseObject(result, PosGoodsFlowEntity.class);
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
                            posGoodsFlowEntity.setSyncFlag(saleDailyEntity.getSyncFlag());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(posGoodsFlowEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(saleDailyEntity));
                        }
                    }
                }else{
                    System.out.println("sale_daily无需要同步的数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
