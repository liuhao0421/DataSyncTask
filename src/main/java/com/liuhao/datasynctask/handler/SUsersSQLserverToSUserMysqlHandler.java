package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.SaleGoodsSummaryEntity;
import com.liuhao.datasynctask.entity.SaleProidSummaryEntity;
import com.liuhao.datasynctask.entity.SysUsersEntity;
import com.liuhao.datasynctask.entity.SysUsersEntitySQLserver;
import com.liuhao.datasynctask.service.SaleProidSummaryService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import com.liuhao.datasynctask.service.impl.SysUsersServiceImplSQLserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
public class SUsersSQLserverToSUserMysqlHandler {
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    public SysUsersServiceImplSQLserver dataSyncService;
    @Autowired
    BeginHandler beginHandler;
    //修改同步
    public void syncTask(){
        try{
            while(true){
                String sourceData = dataSyncService.getDataFromSource();
                if(sourceData!=null){
                    List<SysUsersEntitySQLserver> sysUsersEntitySQLserverList = JSONObject.parseArray(sourceData, SysUsersEntitySQLserver.class);
                    for (SysUsersEntitySQLserver sysUsersEntitySQLserver : sysUsersEntitySQLserverList) {
                        //不存在，则插入，存在，则更新envelope_status
                        String result = dataSyncService.selectIsExist(JSONObject.toJSONString(sysUsersEntitySQLserver));
                        if(result==null||result.length()==0){
                            //目标表中没有该条数据，进行插入操作
                            String syncedData = dataSyncService.pushDataToTarget(JSONObject.toJSONString(sysUsersEntitySQLserver));
                            dataSyncService.updateSourceData(syncedData);
                        }else{
                            //目标表中有该条数据，进行更新值操作
                            SysUsersEntity sysUsersEntity = JSONObject.parseObject(result, SysUsersEntity.class);
                            //TODO company_id需要另外写
                            sysUsersEntity.setCompanyId(beginHandler.getCompanId());
                            sysUsersEntity.setStoreId(sysUsersEntitySQLserver.getStoreId());
                            sysUsersEntity.setUserId(sysUsersEntitySQLserver.getUserId());
                            sysUsersEntity.setUserName(sysUsersEntitySQLserver.getUserName());
                            sysUsersEntity.setPwd(sysUsersEntitySQLserver.getPwd());
                            sysUsersEntity.setStatus(sysUsersEntitySQLserver.getStatus());
                            sysUsersEntity.setSex(sysUsersEntitySQLserver.getSex());
                            sysUsersEntity.setBirthday(sysUsersEntitySQLserver.getBirthday());
                            sysUsersEntity.setAddr(sysUsersEntitySQLserver.getAddr());
                            sysUsersEntity.setTel(sysUsersEntitySQLserver.getTel());
                            sysUsersEntity.setMobile(sysUsersEntitySQLserver.getMobile());
                            sysUsersEntity.setDiscountRate(sysUsersEntitySQLserver.getDiscountRate());
                            sysUsersEntity.setChangePriceRate(sysUsersEntitySQLserver.getChangePriceRate());
                            sysUsersEntity.setRemark(sysUsersEntitySQLserver.getRemark());
                            sysUsersEntity.setChgpwddate(sysUsersEntitySQLserver.getChgpwddate());
                            sysUsersEntity.setLeavedate(sysUsersEntitySQLserver.getLeavedate());
                            sysUsersEntity.setCreateDate(sysUsersEntitySQLserver.getCreateDate());
                            sysUsersEntity.setUpdateDate(sysUsersEntitySQLserver.getUpdateDate());
                            sysUsersEntity.setOutCashierid(sysUsersEntitySQLserver.getOutCashierid());
                            sysUsersEntity.setCardsalerate(sysUsersEntitySQLserver.getCardsalerate());
                            sysUsersEntity.setSyncFlag(sysUsersEntitySQLserver.getSyncFlag());
                            dataSyncService.updateTargetData(JSONObject.toJSONString(sysUsersEntity));
                            dataSyncService.updateSourceData(JSONObject.toJSONString(sysUsersEntitySQLserver));
                        }
                    }
                }else{
                    System.out.println("sys_users无需要同步的数据！！！！！！！");
                    Thread.sleep(5000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            sendMessageServcice.sendText(e.getMessage());
        }
    }
}
