package com.liuhao.datasynctask.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckService {

    @DS("sqlserver")
    public String getCompanyId(){
        return SqlRunner.db().selectOne("select parmvalue from sysparm where parmid ='0308'").get("parmvalue").toString();
    }
    @DS("mysql")
    public String getStatus(String companyId){
        return SqlRunner.db().selectOne("select company_status from company_list where company_id = "+"'"+companyId+"'").get("company_status").toString();
    }
    @DS("mysql")
    public String getCompanyName(String companyId){
        return SqlRunner.db().selectOne("select company_name from company_list where company_id = "+"'"+companyId+"'").get("company_name").toString();
    }
}
