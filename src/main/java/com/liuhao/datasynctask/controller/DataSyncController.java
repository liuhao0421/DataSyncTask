package com.liuhao.datasynctask.controller;

import com.liuhao.datasynctask.handler.*;
import com.liuhao.datasynctask.service.MemberCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/dataSync")
@Slf4j
@ResponseBody
public class DataSyncController {

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
    @Autowired
    MemberAccountXGSyncHandler memberAccountXGSyncHandler;
    @Autowired
    MemberPointXZSyncHandler memberPointXZSyncHandler;
    @Autowired
    MemberAmtXZSyncHandler memberAmtXZSyncHandler;



    @RequestMapping("/membercardinsert")
    public void membercardinsert(){
        memberCardXZSyncHandler.syncTask();
    }

    @RequestMapping("/membaercardupdate")
    public void membaercardupdate(){
        memberCardXGSyncHandler.syncTask();
    }

    @RequestMapping("/membaeraccountupdate")
    public void membaeraccountupdate(){
        memberAccountXGSyncHandler.syncTask();
    }

    @RequestMapping("/memberpointinsert")
    public void memberpointinsert(){
        memberPointXZSyncHandler.syncTask();
    }

    @RequestMapping("/memberamtinsert")
    public void memberamtinsert(){
        memberAmtXZSyncHandler.syncTask();
    }
}