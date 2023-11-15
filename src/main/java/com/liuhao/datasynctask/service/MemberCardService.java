package com.liuhao.datasynctask.service;

import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.context.annotation.Scope;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhao
 * @since 2023-11-14
 */

public interface MemberCardService extends IService<MemberCardEntity> {

    //获取修改的数据
    String getUpdateDataFromSource();

    //从数据源读取数据,并做标志位标记
    String getDataFromSource();

    //将读取的数据更新到目标数据表
    String pushDataToTarget(String sourceData);

    //更新数据源表的更新时间
    void updateSourceData(String sourceData);

    //将修改的数据同步到目标表
    String updateTargetData(String sourceData);
}
