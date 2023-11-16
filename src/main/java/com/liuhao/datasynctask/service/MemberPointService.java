package com.liuhao.datasynctask.service;

import com.liuhao.datasynctask.entity.MemberPointEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhao
 * @since 2023-11-05
 */
public interface MemberPointService extends IService<MemberPointEntity> {


    //从数据源读取数据,并做标志位标记
    String getDataFromSource();

    //将读取的数据更新到目标数据表
    String pushDataToTarget(String sourceData);

    //更新数据源表的更新时间
    void updateSourceData(String sourceData);
}
