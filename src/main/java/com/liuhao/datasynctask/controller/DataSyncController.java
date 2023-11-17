package com.liuhao.datasynctask.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.liuhao.datasynctask.handler.*;
import com.liuhao.datasynctask.service.MemberCardService;
import com.liuhao.datasynctask.service.impl.WeChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/dataSync")
@Slf4j
@ResponseBody
public class DataSyncController {
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
    @Autowired
    VCSyncToRedHandler vcSyncToRedHandler;
    @Autowired
    ProductSyncToGoodsHandler productSyncToGoodsHandler;
    @Autowired
    ProBarSyncToGoodsMCGoodsHandler proBarSyncToGoodsMCGoodsHandler;
    @Autowired
    SupplierSyncToGoodsSupplierHandler supplierSyncToGoodsSupplierHandler;
    @Autowired
    ProClassToGoodsClassHandler proClassToGoodsClassHandler;
    @Autowired
    SaleDToPosGFHandler saleDToPosGFHandler;
    @Autowired
    SalePToPosPFHandler salePToPosPFHandler;
    @Autowired
    SProidToSGSHandler sProidToSGSHandler;
    @Autowired
    SUsersSQLserverToSUserMysqlHandler sUsersSQLserverToSUserMysqlHandler;





    @RequestMapping("/membercardinsert")
    public void membercardinsert(){
        memberCardXZSyncHandler.syncTask();
    }

    @RequestMapping("/membercardupdate")
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

    @RequestMapping("/vctoredsync")
    public void vctoredsync(){
        vcSyncToRedHandler.syncTask();
    }

    @RequestMapping("/producttogoodssync")
    public void producttogoodssync(){
        productSyncToGoodsHandler.syncTask();
    }

    @RequestMapping("/probartogoodsmcsync")
    public void probartogoodsmcsync(){
        proBarSyncToGoodsMCGoodsHandler.syncTask();
    }

    @RequestMapping("/suppliertogoodssuppliersync")
    public void suppliertogoodssuppliersync(){
        supplierSyncToGoodsSupplierHandler.syncTask();
    }

    @RequestMapping("/proclasstogoodsclasssync")
    public void proclasstogoodsclasssync(){
        proClassToGoodsClassHandler.syncTask();
    }

    @RequestMapping("/saledtoposgfsync")
    public void saledtoposgfsync(){
        saleDToPosGFHandler.syncTask();
    }

    @RequestMapping("/saleptopospfsync")
    public void saleptopospfsync(){
        salePToPosPFHandler.syncTask();
    }

    @RequestMapping("/salepstosalegssync")
    public void salepstosalegssync(){
        salePToPosPFHandler.syncTask();
    }

    @RequestMapping("/susqlservertosumysqlsync")
    public void susqlservertosumysqlsync(){
        sUsersSQLserverToSUserMysqlHandler.syncTask();
    }


}
