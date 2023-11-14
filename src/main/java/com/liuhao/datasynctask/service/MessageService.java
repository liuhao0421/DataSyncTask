package com.liuhao.datasynctask.service;


import com.liuhao.datasynctask.entity.Message;

public interface MessageService {

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(Message message);

}
