package com.liuhao.datasynctask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.entity.RedEnvelopeEntity;
import com.liuhao.datasynctask.entity.VCouponListUpEntity;
import com.liuhao.datasynctask.mapper.MemberCardMapper;
import com.liuhao.datasynctask.mapper.RedEnvelopeMapper;
import com.liuhao.datasynctask.mapper.VCouponListUpMapper;
import com.liuhao.datasynctask.service.VCouponListUpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhao
 * @since 2023-11-05
 */
@Service
@Slf4j
public class VCouponListUpServiceImpl extends ServiceImpl<VCouponListUpMapper, VCouponListUpEntity> implements VCouponListUpService {



    @Autowired
    private VCouponListUpMapper vCouponListUpMapper;

    @Autowired
    private RedEnvelopeMapper redEnvelopeMapper;

    //获取数据源新增的数据
    @Override
    @DS("sqlserver")
    public String getDataFromSource() {
        List<VCouponListUpEntity> vCouponListUpEntityList = null;
        try{
            //读取需要同步的数据
            vCouponListUpEntityList =  vCouponListUpMapper.getData();
            //将读取了的数据，做标志
            for (VCouponListUpEntity vCouponListUpEntity : vCouponListUpEntityList) {
                vCouponListUpEntity.setSyncFlag("1");
                QueryWrapper<VCouponListUpEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("appid",vCouponListUpEntity.getAppid());
                queryWrapper.eq("couponid",vCouponListUpEntity.getCouponid());
                queryWrapper.eq("pcashvalue",vCouponListUpEntity.getPcashvalue());
                vCouponListUpMapper.update(vCouponListUpEntity,queryWrapper);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(vCouponListUpEntityList==null||vCouponListUpEntityList.size()==0){
            return null;
        }else{
            return JSONObject.toJSONString(vCouponListUpEntityList);
        }
    }

    @Override
    @DS("mysql")
    public String selectIsExist(String sourceData) {
        try{
            VCouponListUpEntity vCouponListUpEntity = JSONObject.parseObject(sourceData, VCouponListUpEntity.class);
            QueryWrapper<RedEnvelopeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("appid",vCouponListUpEntity.getAppid());
            queryWrapper.eq("cardid",vCouponListUpEntity.getCouponid());
            queryWrapper.eq("envelope_amt",vCouponListUpEntity.getPcashvalue());
            RedEnvelopeEntity redEnvelopeEntity = redEnvelopeMapper.selectOne(queryWrapper);
            if(redEnvelopeEntity==null){
                return null;
            }else{
                return JSONObject.toJSONString(redEnvelopeEntity);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }


    //将新增的数据同步至目标表
    @Override
    @DS("mysql")
    public String pushDataToTarget(String sourceData) {
        try{
            VCouponListUpEntity vCouponListUpEntity = JSONObject.parseObject(sourceData, VCouponListUpEntity.class);
            RedEnvelopeEntity redEnvelopeEntity = new RedEnvelopeEntity();
            redEnvelopeEntity.setAppid(vCouponListUpEntity.getAppid());
            redEnvelopeEntity.setOpenid(vCouponListUpEntity.getOpenid());
            redEnvelopeEntity.setCardid(vCouponListUpEntity.getCouponid());
            redEnvelopeEntity.setEnvelopeAmt(BigDecimal.valueOf(vCouponListUpEntity.getPcashvalue()));
            redEnvelopeEntity.setEnvelopeType(vCouponListUpEntity.getCoupontype());
            redEnvelopeEntity.setEnvelopeStatus(vCouponListUpEntity.getStatus());
            redEnvelopeEntity.setEnvelopeSource(vCouponListUpEntity.getSource());
            redEnvelopeEntity.setCreatedate(vCouponListUpEntity.getAuditdate());
            redEnvelopeEntity.setGettime(vCouponListUpEntity.getPrintdate());
            redEnvelopeEntity.setEnvelopeStarttime(vCouponListUpEntity.getStartdate());
            redEnvelopeEntity.setEnvelopeEndtime(vCouponListUpEntity.getEnddate());
            redEnvelopeEntity.setOrderAmt(BigDecimal.valueOf(vCouponListUpEntity.getFullamt()));
            redEnvelopeEntity.setAwarduuid(vCouponListUpEntity.getPcashid());
            redEnvelopeEntity.setRemark(vCouponListUpEntity.getPcashDescription());
            redEnvelopeEntity.setAwardId(BigDecimal.valueOf(vCouponListUpEntity.getAwardId()));
            redEnvelopeEntity.setUpdatedate(vCouponListUpEntity.getUpdatedate());
            redEnvelopeEntity.setUseDate(vCouponListUpEntity.getUseddate());
            redEnvelopeEntity.setCompanyId(BigDecimal.valueOf(vCouponListUpEntity.getCompanyId()));
            redEnvelopeEntity.setSyncTime(LocalDateTime.now());
            redEnvelopeEntity.setSyncFlag(vCouponListUpEntity.getSyncFlag());
            redEnvelopeMapper.insert(redEnvelopeEntity);
            return JSONObject.toJSONString(vCouponListUpEntity);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    //将修改的数据同步至目标表
    @Override
    @DS("mysql")
    public void updateTargetData(String sourceData) {
        try{
            RedEnvelopeEntity redEnvelopeEntity = JSONObject.parseObject(sourceData, RedEnvelopeEntity.class);
            redEnvelopeEntity.setSyncTime(LocalDateTime.now());
            QueryWrapper<RedEnvelopeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("appid",redEnvelopeEntity.getAppid());
            queryWrapper.eq("cardid",redEnvelopeEntity.getCardid());
            queryWrapper.eq("envelope_amt",redEnvelopeEntity.getEnvelopeAmt());
            redEnvelopeMapper.update(redEnvelopeEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }



    //同步结束后修改数据源表的状态
    @Override
    @DS("sqlserver")
    public void updateSourceData(String sourceData) {
        try{
            VCouponListUpEntity vCouponListUpEntity = JSONObject.parseObject(sourceData, VCouponListUpEntity.class);
            vCouponListUpEntity.setSyncTime(LocalDateTime.now());
            vCouponListUpEntity.setSyncFlag("0");
            QueryWrapper<VCouponListUpEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("appid",vCouponListUpEntity.getAppid());
            queryWrapper.eq("couponid",vCouponListUpEntity.getCouponid());
            queryWrapper.eq("pcashvalue",vCouponListUpEntity.getPcashvalue());
            vCouponListUpMapper.update(vCouponListUpEntity,queryWrapper);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
