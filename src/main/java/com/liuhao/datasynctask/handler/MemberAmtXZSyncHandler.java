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
    @Autowired
    BeginHandler beginHandler;
    //新增同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource(beginHandler.getCompanId());
                if(sourceData!=null){
                    List<MemberAmtEntity> memberAmtEntityList = JSONObject.parseArray(sourceData, MemberAmtEntity.class);
                    for (MemberAmtEntity memberAmtEntity : memberAmtEntityList) {
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(memberAmtEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(memberAmtEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(memberAmtEntity));
                        }else{
                            //重复的跳过，将sync_flag重新置为0以及更新sync_time
                            dataSyncService.updateSourceData(JSONObject.toJSONString(memberAmtEntity));
                        }
                    }
                }else{
                    log.info("member_amt无新增数据！！！！！！！");
                    dataSyncService.backSyncFalg(beginHandler.getCompanId());
                    Thread.sleep(150000);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            //sendMessageServcice.sendText(e.getMessage());
        }
    }
}
