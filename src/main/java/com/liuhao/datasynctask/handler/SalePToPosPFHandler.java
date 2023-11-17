package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.PosGoodsFlowEntity;
import com.liuhao.datasynctask.entity.PosPayFlowEntity;
import com.liuhao.datasynctask.entity.SaleDailyEntity;
import com.liuhao.datasynctask.entity.SalePaymodeEntity;
import com.liuhao.datasynctask.service.SaleDailyService;
import com.liuhao.datasynctask.service.SalePaymodeService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
public class SalePToPosPFHandler {
    
    @Autowired
    SendMessageServcice sendMessageServcice;
    
    @Autowired
    public SalePaymodeService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<SalePaymodeEntity> salePaymodeEntityList = JSONObject.parseArray(sourceData, SalePaymodeEntity.class);
                    for (SalePaymodeEntity salePaymodeEntity : salePaymodeEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(salePaymodeEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(salePaymodeEntity));
                            dataSyncService.updateSourceData(syncedData);
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            PosPayFlowEntity posPayFlowEntity = JSONObject.parseObject(result, PosPayFlowEntity.class);
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
                            posPayFlowEntity.setSyncFlag(salePaymodeEntity.getSyncFlag());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(posPayFlowEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(salePaymodeEntity));
                        }
                    }
                }else{
                    System.out.println("sale_paymode无需要同步的数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            sendMessageServcice.sendText(e.getMessage());
            
        }
    }
}
