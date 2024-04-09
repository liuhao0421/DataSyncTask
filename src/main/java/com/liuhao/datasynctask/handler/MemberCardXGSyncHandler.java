package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.util.PushUtil;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.service.MemberCardService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class MemberCardXGSyncHandler{
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public MemberCardService dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getUpdateDataFromSource(beginHandler.getCompanId());
                if(sourceData!=null){
                    List<MemberCardEntity> memberCardEntityList = JSONObject.parseArray(sourceData, MemberCardEntity.class);
                    for (MemberCardEntity memberCardEntity : memberCardEntityList) {
                        String syncedData = dataSyncService.updateTargetData(JSONObject.toJSONString(memberCardEntity));
                        dataSyncService.updateSourceData(JSONObject.toJSONString(memberCardEntity));
                    }
                }else{
                    log.info("member_card无修改数据！！！！！！！");
                    dataSyncService.backSyncFalg(beginHandler.getCompanId());
                    Thread.sleep(200000);

                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            PushUtil.push(beginHandler.getCompanName()+", 数据同步存在异常");
        }
    }
}
