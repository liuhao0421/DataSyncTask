package com.liuhao.datasynctask.service;

import com.liuhao.datasynctask.entity.SupplierEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhao
 * @since 2023-11-05
 */
public interface SupplierService extends IService<SupplierEntity> {


    //从数据源读取数据,并做标志位标记
    String getDataFromSource();

    //判断目标表是否存在该记录
    String selectIsExist(String sourceData);


    //将读取的数据更新到目标数据表
    String pushDataToTarget(String sourceData);


    //将修改的数据同步到目标表
    void updateTargetData(String sourceData);

    //更新数据源表的更新时间
    void updateSourceData(String sourceData);
    //恢复未同步数据
    void backSyncFalg();
}
