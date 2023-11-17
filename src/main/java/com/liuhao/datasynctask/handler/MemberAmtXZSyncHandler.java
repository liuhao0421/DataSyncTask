package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.MemberAmtEntity;
import com.liuhao.datasynctask.service.MemberAmtService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
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
                    System.out.println("member_Amt无新增数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            sendMessageServcice.sendText(e.getMessage());
        }
    }
}
