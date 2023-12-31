package com.liuhao.datasynctask.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SendMessageServcice {


    @Autowired
    private WeChatMessageService weChatMessageService;

    public String sendText(String content) {


        String token = weChatMessageService.getAccessToken();
        //发送消息
        return weChatMessageService.sendMessage(token, content);


    }
}
