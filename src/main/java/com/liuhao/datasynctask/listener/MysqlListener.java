package com.liuhao.datasynctask.listener;


import com.liuhao.datasynctask.entity.ChangeData;
import com.liuhao.datasynctask.entity.Message;
import com.liuhao.datasynctask.service.MessageService;
import com.liuhao.datasynctask.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.debezium.engine.format.Json;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class MysqlListener {

    @Resource
    private MessageService messageService;

    private final List<DebeziumEngine<ChangeEvent<String, String>>> engineList = new ArrayList<>();

    private MysqlListener(@Qualifier("mysqlProperties") List<Properties> list) {
        for (Properties props : list) {
            this.engineList.add(DebeziumEngine.create(Json.class)
                    .using(props)
                    .notifying(record -> {
                        receiveChangeEvent(record.value(), props.getProperty("debezium.name"));
                    }).build());
        }
    }

    private void receiveChangeEvent(String value, String name) {
        if (Objects.nonNull(value)) {
            Map<String, Object> payload = JSONUtils.getPayload(value);
            String handleType = JSONUtils.getHandleType(payload);
            /*if (!("NONE".equals(handleType) || "READ".equals(handleType))) {
                ChangeData changeData = JSONUtils.getChangeData(payload);
                Map<String, Object> data;
                if ("DELETE".equals(handleType)) {
                    data = changeData.getBefore();
                } else {
                    data = changeData.getAfter();
                }
                Message build = Message.builder()
                        .data(data)
                        .dbType("Mysql")
                        .database(String.valueOf(changeData.getSource().get("db")))
                        .table(String.valueOf(changeData.getSource().get("table")))
                        .handleType(handleType)
                        .build();
                log.info("【Debezium-" + name + "】" + build.toString());
                messageService.sendMessage(build);
            }*/
                ChangeData changeData = JSONUtils.getChangeData(payload);
                Map<String, Object> data;
                if ("DELETE".equals(handleType)) {
                    data = changeData.getBefore();
                    Message build = Message.builder()
                            .data(data)
                            .dbType("Mysql")
                            .database(String.valueOf(changeData.getSource().get("db")))
                            .table(String.valueOf(changeData.getSource().get("table")))
                            .handleType(handleType)
                            .build();
                    if("member_card".equals(build.getTable())){
                        messageService.sendMessage(build);
                    }
                }
        }
    }

    @PostConstruct
    private void start() {
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            Executors.newSingleThreadExecutor().execute(engine);
        }
    }

    @PreDestroy
    private void stop() {
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            if (engine != null) {
                try {
                    engine.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
