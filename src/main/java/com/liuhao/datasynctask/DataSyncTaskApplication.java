package com.liuhao.datasynctask;

import com.liuhao.datasynctask.handler.MemberCardXGSyncHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class DataSyncTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSyncTaskApplication.class, args);
        log.info(" = = = = DataSyncTask = = = = 启动成功 ！！！");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
