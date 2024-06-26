package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.util.PushUtil;
import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.liuhao.datasynctask.service.MemberPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class MemberPointXZSyncHandler {
    
    @Autowired
    public MemberPointService dataSyncService;
    @Autowired
    public BeginHandler beginHandler;
    //新增同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<MemberPointEntity> memberPointEntityList = JSONObject.parseArray(sourceData, MemberPointEntity.class);
                    for (MemberPointEntity memberPointEntity : memberPointEntityList) {
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(memberPointEntity));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(memberPointEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(memberPointEntity));
                        }else{
                            //重复的跳过，将sync_flag重新置为0以及更新sync_time
                            dataSyncService.updateSourceData(JSONObject.toJSONString(memberPointEntity));
                        }
                    }
                }else{
                    log.info("member_point无新增数据！！！！！！！");
                    dataSyncService.backSyncFalg();
                    Thread.sleep(300000);

                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            PushUtil.push(beginHandler.getCompanName()+", 数据同步存在异常");
        }
    }
}
