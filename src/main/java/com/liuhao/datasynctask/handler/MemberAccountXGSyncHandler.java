package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.MemberAccountEntity;
import com.liuhao.datasynctask.service.MemberAccountService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
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
                        String syncedData = dataSyncService.updateTargetData(JSONObject.toJSONString(memberAccountEntity));
                        dataSyncService.updateSourceData(syncedData);
                    }
                }else{
                    System.out.println("member_account无修改数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            sendMessageServcice.sendText(e.getMessage());
        }
    }
}
