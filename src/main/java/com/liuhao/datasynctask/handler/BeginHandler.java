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

        Thread memberCardXZThread = new Thread(beginHandler);
        memberCardXZThread.setName("memberCardXZ");
        memberCardXZThread.start();

        Thread memberCardXGThread = new Thread(beginHandler);
        memberCardXGThread.setName("memberCardXG");
        memberCardXGThread.start();

        Thread memberAccountXGThread = new Thread(beginHandler);
        memberAccountXGThread.setName("memberAccountXG");
        memberAccountXGThread.start();

        Thread memberPointXZThread = new Thread(beginHandler);
        memberPointXZThread.setName("memberPointXZ");
        memberPointXZThread.start();

        Thread memberAmtXZThread = new Thread(beginHandler);
        memberAmtXZThread.setName("memberAmtXZ");
        memberAmtXZThread.start();

    }



    @Override
    public void run() {
        String url = null;
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        RestTemplate template = new RestTemplate();
        try {
            String name = Thread.currentThread().getName();
            if("memberCardXZ".equals(name)){
                url = "http://localhost:23850/dataSync/membercardinsert";
            }else if("memberCardXG".equals(name)){
                url = "http://localhost:23850/dataSync/membercardupdate";
            }else if("memberAccountXG".equals(name)){
                url = "http://localhost:23850/dataSync/membaeraccountupdate";
            }else if("memberPointXG".equals(name)){
                url = "http://localhost:23850/dataSync/memberpointinsert";
            }else if("memberAmtXZ".equals(name)){
                url = "http://localhost:23850/dataSync/memberamtinsert";
            }
            template.postForObject(url, paramMap, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
