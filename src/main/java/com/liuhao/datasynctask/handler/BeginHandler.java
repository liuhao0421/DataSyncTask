package com.liuhao.datasynctask.handler;

//import com.liuhao.datasynctask.feign.LocalFeign;
import com.liuhao.datasynctask.util.PushUtil;
import com.liuhao.datasynctask.service.impl.CheckService;
import com.liuhao.datasynctask.service.impl.SendMessageServcice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class BeginHandler implements ApplicationRunner,Runnable{
    @Autowired
    SendMessageServcice sendMessageServcice;
    @Autowired
    CheckService checkService;

    String companyId = "";
    String companyName = "";

    @Override
    public void run(ApplicationArguments args) {

        companyId = checkService.getCompanyId();
        String status = checkService.getStatus(companyId);
        companyName = checkService.getCompanyName(companyId);
        if("1".equals(status)){
           log.error("当前门店的company_id是==="+ companyId+" &&& 当前门店的company_stautus是==="+status);
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
            log.error("当前门店的company_id是==="+ companyId+" &&& 当前门店的company_stautus是==="+status);
            PushUtil.push(companyName+", 当前连接的商家，连接不合法");
        }


    }


    public String getCompanId(){
        return this.companyId;
    }
    public String getCompanName(){
        return this.companyName;
    }



    @Override
    public void run() {
        String url = null;
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        RestTemplate template = new RestTemplate();
        try {
            String name = Thread.currentThread().getName();
            if("memberCardXZ".equals(name)){
                url = "http://localhost:23851/dataSync/membercardinsert";
            }else if("memberCardXG".equals(name)){
                url = "http://localhost:23851/dataSync/membercardupdate";
            }else if("memberAccountXG".equals(name)){
                url = "http://localhost:23851/dataSync/membaeraccountupdate";
            }else if("memberPointXZ".equals(name)){
                url = "http://localhost:23851/dataSync/memberpointinsert";
            }else if("memberAmtXZ".equals(name)){
                url = "http://localhost:23851/dataSync/memberamtinsert";
            }else if("vcToRedSync".equals(name)){
                url = "http://localhost:23851/dataSync/vctoredsync";
            }else if("productToGoodsSync".equals(name)){
                url = "http://localhost:23851/dataSync/producttogoodssync";
            }else if("proBarToGoodsMCSync".equals(name)){
                url = "http://localhost:23851/dataSync/probartogoodsmcsync";
            }else if("supplierToGoodsSupplierSync".equals(name)){
                url = "http://localhost:23851/dataSync/suppliertogoodssuppliersync";
            }else if("proClassToGoodsClassSync".equals(name)){
                url = "http://localhost:23851/dataSync/proclasstogoodsclasssync";
            }else if("sDailyToPosGFSync".equals(name)){
                url = "http://localhost:23851/dataSync/saledtoposgfsync";
            }else if("sPayModeToPosPFSync".equals(name)){
                url = "http://localhost:23851/dataSync/saleptopospfsync";
            }else if("salePSToSaleGSSync".equals(name)){
                url = "http://localhost:23851/dataSync/salepstosalegssync";
            }else if("sUSqlserverTosUMysqlSync".equals(name)){
                url = "http://localhost:23851/dataSync/susqlservertosumysqlsync";
            }
            template.postForObject(url, paramMap, String.class);
            PushUtil.push(companyName+", 数据同步任务启动成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            PushUtil.push(companyName+", 数据同步任务启动存在异常");
        }
    }

}
