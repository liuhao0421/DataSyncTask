package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.liuhao.datasynctask.service.MemberPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class MemberPointXZSyncHandler {
    @Autowired
    public MemberPointService dataSyncService;
    //新增同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<MemberPointEntity> memberPointEntityList = JSONObject.parseArray(sourceData, MemberPointEntity.class);
                    for (MemberPointEntity memberPointEntity : memberPointEntityList) {
                        String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(memberPointEntity));
                        dataSyncService.updateSourceData(syncedData);
                    }
                }else{
                    System.out.println("member_point无新增数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
