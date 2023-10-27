package com.liuhao.datasynctask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhao.datasynctask.util.ConvertUtil;
import com.liuhao.datasynctask.entity.MemberCard;
import com.liuhao.datasynctask.entity.MemberCardEntityMySQL;
import com.liuhao.datasynctask.mapper.MemberCardMapper;
import com.liuhao.datasynctask.mapper.MemberCardMapperMySQL;
import com.liuhao.datasynctask.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DataSyncServiceImpl implements DataSyncService {




    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private MemberCardMapperMySQL memberCardMapperMySQL;


    @Override
    public String dataSourceTestForMysql(MemberCard memberCard) {
        MemberCardEntityMySQL memberCardEntityMySQL = ConvertUtil.ConvertClass(memberCard, MemberCardEntityMySQL.class);
        int insert = memberCardMapperMySQL.insert(memberCardEntityMySQL);
        return insert+"";

    }

    @Override
    public String dataSourceTestForSqlServer() {
        QueryWrapper<MemberCard> wrapper = new QueryWrapper<>();
        wrapper.eq("mem_id","1");
        List<MemberCard> memberCards = memberCardMapper.selectList(wrapper);
        return sqlerverTomysql(memberCards.get(0));
    }

    private String sqlerverTomysql(MemberCard memberCard){
       return dataSourceTestForMysql(memberCard);
    }


}
