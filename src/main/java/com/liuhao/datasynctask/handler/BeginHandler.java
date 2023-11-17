package com.liuhao.datasynctask.handler;

//import com.liuhao.datasynctask.feign.LocalFeign;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.liuhao.datasynctask.service.impl.CheckService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Slf4j
@Component
public class BeginHandler implements ApplicationRunner,Runnable{
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    CheckService checkService;


    @Override
    public void run(ApplicationArguments args) {


        BeginHandler beginHandler = new BeginHandler();

/*        Thread memberCardXZThread = new Thread(beginHandler);
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
        proClassToGoodsClassSyncThread.start();*/


       Thread sDailyToPosGFSyncThread = new Thread(beginHandler);
        sDailyToPosGFSyncThread.setName("sDailyToPosGFSync");
        sDailyToPosGFSyncThread.start();

        Thread sPayModeToPosPFSyncThread = new Thread(beginHandler);
        sPayModeToPosPFSyncThread.setName("sPayModeToPosPFSync");
        sPayModeToPosPFSyncThread.start();

        Thread salePSToSaleGSSyncThread = new Thread(beginHandler);
        salePSToSaleGSSyncThread.setName("salePSToSaleGSSync");
        salePSToSaleGSSyncThread.start();


        Thread sUSqlserverTosUMysqlSyncThread = new Thread(beginHandler);
        sUSqlserverTosUMysqlSyncThread.setName("sUSqlserverTosUMysqlSync");
        sUSqlserverTosUMysqlSyncThread.start();





     /*   String companyId = checkService.getCompanyId();
        String status = checkService.getStatus(companyId);
        if("1".equals(status)){

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

        Thread salePSToSaleGSSyncThread = new Thread(beginHandler);
        salePSToSaleGSSyncThread.setName("salePSToSaleGSSync");
        salePSToSaleGSSyncThread.start();


        Thread sUSqlserverTosUMysqlSyncThread = new Thread(beginHandler);
        sUSqlserverTosUMysqlSyncThread.setName("sUSqlserverTosUMysqlSync");
        sUSqlserverTosUMysqlSyncThread.start();
        }else{
            log.error("当前连接的商家，连接不合法");
        }

*/
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
            }else if("memberPointXZ".equals(name)){
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
                url = "http://localhost:23850/dataSync/saleptopospfsync";
            }else if("salePSToSaleGSSync".equals(name)){
                url = "http://localhost:23850/dataSync/salepstosalegssync";
            }else if("sUSqlserverTosUMysqlSync".equals(name)){
                url = "http://localhost:23850/dataSync/susqlservertosumysqlsync";
            }
            template.postForObject(url, paramMap, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            sendMessageServcice.sendText(e.getMessage());
        }
    }

}
