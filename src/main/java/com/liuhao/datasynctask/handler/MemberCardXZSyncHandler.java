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
    //新增同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<MemberCardEntity> memberCardEntityList = JSONObject.parseArray(sourceData, MemberCardEntity.class);
                    for (MemberCardEntity memberCardEntity : memberCardEntityList) {
                        String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(memberCardEntity));
                        dataSyncService.updateSourceData(syncedData);
                    }
                }else{
                    log.info("member_card_test无新增数据！！！！！！！");
                    Thread.sleep(30000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
