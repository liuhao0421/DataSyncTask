package com.liuhao.datasynctask.service;

import com.liuhao.datasynctask.entity.MemberCard;

public interface DataSyncService {

    String dataSourceTestForMysql(MemberCard memberCar);
    String dataSourceTestForSqlServer();
}
