package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.MemberCardDeletedEntity;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.mapper.MemberCardDeletedMapper;
import com.liuhao.datasynctask.mapper.MemberCardMapper;
import com.liuhao.datasynctask.service.MemberCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@Slf4j
public class MemberCardDeleteHandler {
    @Autowired
    MemberCardDeletedMapper memberCardDeletedMapper;
    @Autowired
    MemberCardMapper memberCardMapper;
    @Autowired
    BeginHandler beginHandler;

    @Autowired
    MemberCardService memberCardService;
   public void deleteFromMemberCard(String data){

        memberCardService.beforeDelete(data);
        memberCardService.localDelete(data);
    }
}
