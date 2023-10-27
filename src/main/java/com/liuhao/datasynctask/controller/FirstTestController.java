package com.liuhao.datasynctask.controller;

import com.liuhao.datasynctask.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dataSync")
@Slf4j
@ResponseBody
public class FirstTestController {

    @Value("${person.name}")
    private String name;


    @Autowired
    private DataSyncService dataSyncService;


    @RequestMapping("/test1")
    public String test1Controller(){
        log.info(name);
        return name;
    }

    @RequestMapping("/test2")
    public String test2Controller(){

        return dataSyncService.dataSourceTestForSqlServer();
    }


}
