package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.util.PushUtil;
import com.liuhao.datasynctask.entity.RedEnvelopeEntity;
import com.liuhao.datasynctask.entity.VCouponListUpEntity;
import com.liuhao.datasynctask.service.VCouponListUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class VCSyncToRedHandler {
    
    @Autowired
    public VCouponListUpService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<VCouponListUpEntity> vCouponListUpEntityList = JSONObject.parseArray(sourceData, VCouponListUpEntity.class);
                    for (VCouponListUpEntity vCouponListUpEntity : vCouponListUpEntityList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(vCouponListUpEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(vCouponListUpEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(vCouponListUpEntity));
                        }else{
                            //目标表中有该条数据，进行更新envelope_status值操作
                            RedEnvelopeEntity redEnvelopeEntity = JSONObject.parseObject(result, RedEnvelopeEntity.class);
                            redEnvelopeEntity.setEnvelopeStatus(vCouponListUpEntity.getStatus());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(redEnvelopeEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(vCouponListUpEntity));
                        }
                    }
                }else{
                    log.info("v_Coupon_List_Up无需要同步的数据！！！！！！！");
                    dataSyncService.backSyncFalg();
                    Thread.sleep(750000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            PushUtil.push(beginHandler.getCompanName()+", 数据同步存在异常");
        }
    }
}
