package com.liuhao.datasynctask.service;

import com.liuhao.datasynctask.entity.MemberAccountEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhao
 * @since 2023-10-29
 */
public interface MemberAccountService extends IService<MemberAccountEntity> {

    //获取修改的数据
    String getUpdateDataFromSource();

    //更新数据源表的更新时间
    void updateSourceData(String sourceData);

    //将修改的数据同步到目标表
    String updateTargetData(String sourceData);
}
