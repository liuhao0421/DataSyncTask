package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liuhao.datasynctask.entity.MemberCardDeletedEntity;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.entity.Message;
import com.liuhao.datasynctask.handler.MemberCardDeleteHandler;
import com.liuhao.datasynctask.service.MessageService;

import io.debezium.engine.format.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MemberCardDeleteHandler memberCardDeleteHandler;

    @Override
    public void sendMessage(Message message) {
        Map<String, Object> data = message.getData();
        MemberCardDeletedEntity memberCardDeletedEntity = JSONObject.parseObject(JSONObject.toJSONString(data), MemberCardDeletedEntity.class);
        memberCardDeleteHandler.deleteFromMemberCard(JSONObject.toJSONString(memberCardDeletedEntity));
    }

}
