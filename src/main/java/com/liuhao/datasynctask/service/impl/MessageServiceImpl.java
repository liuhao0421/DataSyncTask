package com.liuhao.datasynctask.service.impl;

import com.liuhao.datasynctask.entity.Message;
import com.liuhao.datasynctask.service.MessageService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class MessageServiceImpl implements MessageService {


    @Override
    public void sendMessage(Message message) {
        System.out.println(message);
//        String exchange = "FANOUT.ORACLE.CHANGE." + message.getTable();
//        log.info("【RabbitMQ】Send message to '" + exchange);
//        rabbitTemplate.convertAndSend(exchange, null, JSON.toJSONString(message));
    }
}
