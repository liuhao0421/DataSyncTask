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

        Thread vcToRedSyncThread = new Thread(beginHandler);
        vcToRedSyncThread.setName("vcToRedSync");
        vcToRedSyncThread.start();

        Thread productToGoodsSyncThread = new Thread(beginHandler);
        productToGoodsSyncThread.setName("productToGoodsSync");
        productToGoodsSyncThread.start();

        Thread proBarToGoodsMCSyncThread = new Thread(beginHandler);
        proBarToGoodsMCSyncThread.setName("proBarToGoodsMCSync");
        proBarToGoodsMCSyncThread.start();

        Thread supplierToGoodsSupplierSyncThread = new Thread(beginHandler);
        supplierToGoodsSupplierSyncThread.setName("supplierToGoodsSupplierSync");
        supplierToGoodsSupplierSyncThread.start();

        Thread proClassToGoodsClassSyncThread = new Thread(beginHandler);
        proClassToGoodsClassSyncThread.setName("proClassToGoodsClassSync");
        proClassToGoodsClassSyncThread.start();


        Thread sDailyToPosGFSyncThread = new Thread(beginHandler);
        sDailyToPosGFSyncThread.setName("sDailyToPosGFSync");
        sDailyToPosGFSyncThread.start();

        Thread sPayModeToPosPFSyncThread = new Thread(beginHandler);
        sPayModeToPosPFSyncThread.setName("sPayModeToPosPFSync");
        sPayModeToPosPFSyncThread.start();

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
            }else if("vcToRedSync".equals(name)){
                url = "http://localhost:23850/dataSync/vctoredsync";
            }else if("productToGoodsSync".equals(name)){
                url = "http://localhost:23850/dataSync/producttogoodssync";
            }else if("proBarToGoodsMCSync".equals(name)){
                url = "http://localhost:23850/dataSync/probartogoodsmcsync";
            }else if("supplierToGoodsSupplierSync".equals(name)){
                url = "http://localhost:23850/dataSync/suppliertogoodssuppliersync";
            }else if("proClassToGoodsClassSync".equals(name)){
                url = "http://localhost:23850/dataSync/proclasstogoodsclasssync";
            }else if("sDailyToPosGFSync".equals(name)){
                url = "http://localhost:23850/dataSync/saledtoposgfsync";
            }else if("sPayModeToPosPFSync".equals(name)){
                url = "http://localhost:23850/dataSync/saledtoposgfsync";
            }
            template.postForObject(url, paramMap, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
