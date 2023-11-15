package com.liuhao.datasynctask.controller;

import com.liuhao.datasynctask.handler.MemberCardXGSyncHandler;
import com.liuhao.datasynctask.handler.MemberCardXZSyncHandler;
import com.liuhao.datasynctask.service.MemberCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/dataSync")
@Slf4j
@ResponseBody
public class FirstTestController {

    @Value("${person.name}")
    private String name;
    @Autowired
    private RestTemplate template;

    @Autowired
    private MemberCardService dataSyncService;

    @Autowired
    MemberCardXGSyncHandler memberCardXGSyncHandler;
    @Autowired
    MemberCardXZSyncHandler memberCardXZSyncHandler;
    @RequestMapping("/membercardinsert")
    public void test1Controller(){
        memberCardXZSyncHandler.syncTask();
    }

    @RequestMapping("/membaercardupdate")
    public void test2Controller(){
        memberCardXGSyncHandler.syncTask();
    }
}
