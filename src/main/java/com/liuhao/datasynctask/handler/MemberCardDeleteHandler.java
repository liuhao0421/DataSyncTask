package com.liuhao.datasynctask.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.liuhao.datasynctask.entity.MemberCardDeletedEntity;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.mapper.MemberCardDeletedMapper;
import com.liuhao.datasynctask.mapper.MemberCardMapper;
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
    @DS("mysql")
   public void deleteFromMemberCard(String data){

        //先在member_card_deleted表中将要删除的数据备份
        MemberCardDeletedEntity memberCardDeletedEntity = JSONObject.parseObject(data, MemberCardDeletedEntity.class);
        memberCardDeletedMapper.insert(memberCardDeletedEntity);
        //再将member_card表中的该条记录删除
        MemberCardEntity memberCardEntity = JSONObject.parseObject(data, MemberCardEntity.class);
        memberCardMapper.deleteById(memberCardEntity);
    }
}
