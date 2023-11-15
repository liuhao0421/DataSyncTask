package com.liuhao.datasynctask;

import com.liuhao.datasynctask.handler.MemberCardXGSyncHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DataSyncTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSyncTaskApplication.class, args);

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
