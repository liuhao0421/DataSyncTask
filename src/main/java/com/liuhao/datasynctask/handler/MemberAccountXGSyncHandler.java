package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.MemberAccountEntity;
import com.liuhao.datasynctask.service.MemberAccountService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class MemberAccountXGSyncHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public MemberAccountService dataSyncService;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getUpdateDataFromSource();
                if(sourceData!=null){
                    List<MemberAccountEntity> memberAccountEntityList = JSONObject.parseArray(sourceData, MemberAccountEntity.class);
                    for (MemberAccountEntity memberAccountEntity : memberAccountEntityList) {
                            //已经存在的，修改
                            String syncedData = dataSyncService.updateTargetData(JSONObject.toJSONString(memberAccountEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(memberAccountEntity));
                    }
                }else{
                    log.info("member_account无修改数据！！！！！！！");
                    dataSyncService.backSyncFalg();
                    Thread.sleep(100000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
