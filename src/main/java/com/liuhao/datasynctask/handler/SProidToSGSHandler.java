package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.PosPayFlowEntity;
import com.liuhao.datasynctask.entity.SaleGoodsSummaryEntity;
import com.liuhao.datasynctask.entity.SalePaymodeEntity;
import com.liuhao.datasynctask.entity.SaleProidSummaryEntity;
import com.liuhao.datasynctask.service.SalePaymodeService;
import com.liuhao.datasynctask.service.SaleProidSummaryService;
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
public class SProidToSGSHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public SaleProidSummaryService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<SaleProidSummaryEntity> saleProidSummaryEntityList = JSONObject.parseArray(sourceData, SaleProidSummaryEntity.class);
                    for (SaleProidSummaryEntity saleProidSummaryEntity : saleProidSummaryEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(saleProidSummaryEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(saleProidSummaryEntity));
                            dataSyncService.updateSourceData(syncedData);
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            SaleGoodsSummaryEntity saleGoodsSummaryEntity = JSONObject.parseObject(result, SaleGoodsSummaryEntity.class);
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
                            saleGoodsSummaryEntity.setSalePrice(null);//无值，传空
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
                            saleGoodsSummaryEntity.setSyncFlag(saleProidSummaryEntity.getSyncFlag());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(saleGoodsSummaryEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(saleProidSummaryEntity));
                        }
                    }
                }else{
                    log.info("sale_proid_summary无需要同步的数据！！！！！！！");
                    Thread.sleep(30000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
