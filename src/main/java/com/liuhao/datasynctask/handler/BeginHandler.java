package com.liuhao.datasynctask.handler;

//import com.liuhao.datasynctask.feign.LocalFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class BeginHandler implements ApplicationRunner,Runnable{



    @Override
    public void run(ApplicationArguments args) {
        BeginHandler beginHandler = new BeginHandler();
        Thread xzThread = new Thread(beginHandler);
        xzThread.setName("XZ");
        xzThread.start();
        Thread xgThread = new Thread(beginHandler);
        xgThread.setName("XG");
        xgThread.start();
    }



    @Override
    public void run() {
        String url = null;
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        RestTemplate template = new RestTemplate();
        try {
            String name = Thread.currentThread().getName();
            if("XZ".equals(name)){
                url = "http://localhost:23850/dataSync/membercardinsert";
            }else if("XG".equals(name)){
                url = "http://localhost:23850/dataSync/membercardupdate";
            }
            template.postForObject(url, paramMap, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
