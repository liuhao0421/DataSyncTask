package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.MemberAmtEntity;
import com.liuhao.datasynctask.service.MemberAmtService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class MemberAmtXZSyncHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public MemberAmtService dataSyncService;
    //新增同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<MemberAmtEntity> memberAmtEntityList = JSONObject.parseArray(sourceData, MemberAmtEntity.class);
                    for (MemberAmtEntity memberAmtEntity : memberAmtEntityList) {
                        String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(memberAmtEntity));
                        dataSyncService.updateSourceData(syncedData);
                    }
                }else{
                    log.info("member_amt_test无新增数据！！！！！！！");
                    Thread.sleep(30000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
