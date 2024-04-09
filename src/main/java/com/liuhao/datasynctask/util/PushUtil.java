package com.liuhao.datasynctask.util;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PushUtil {

    public static String push(String message) {

        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("wx420e15d046ba2e9c");
        wxStorage.setSecret("08b5e913e7f3897ed29e09cb4f999546");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("omUfx6g2LheiAiQyGu4pmjDdRzUg")
                .templateId("DMvygyEbgzvQtZa8KwxYfL1QlW64xYRPp09Ga75hbEM")
                .build();

        // 配置你的信息
        templateMessage.addData(new WxMpTemplateData("syncDataTask", message));

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            log.error("推送失败：" + e.getMessage());
            return "推送失败：" + e.getMessage();
        }
        return "推送成功!";
    }
}