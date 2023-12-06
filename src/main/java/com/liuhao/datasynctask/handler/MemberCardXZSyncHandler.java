package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.service.MemberCardService;
import com.liuhao.datasynctask.service.impl.MemberCardServiceImpl;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class MemberCardXZSyncHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public MemberCardService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //新增同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource(beginHandler.getCompanId());
                if(sourceData!=null){
                    List<MemberCardEntity> memberCardEntityList = JSONObject.parseArray(sourceData, MemberCardEntity.class);
                    for (MemberCardEntity memberCardEntity : memberCardEntityList) {
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(memberCardEntity));
                        if(result==null||result.length()==0){
                            //目标表中无该数据
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(memberCardEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(memberCardEntity));
                        }else{
                            //目标表中有该数据
                            String syncedData = dataSyncService.updateTargetData(JSONObject.toJSONString(memberCardEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(memberCardEntity));
                        }

                    }
                }else{
                    log.info("member_card无新增数据！！！！！！！");
                    dataSyncService.backSyncFalg(beginHandler.getCompanId());
                    Thread.sleep(250000);

                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
