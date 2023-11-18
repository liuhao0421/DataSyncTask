package com.liuhao.datasynctask;





import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class GeneratorUtils {


    public static void main(String[] args) {
        autoGenerator();
    }


    public static void autoGenerator() {

        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setDataSource(getDataSourceConfig());
        autoGenerator.setGlobalConfig(getGlobalConfig());
        autoGenerator.setPackageInfo(getPackageInfo());
        autoGenerator.setStrategy(getStrategyConfig());
        autoGenerator.execute();
    }




    public static DataSourceConfig getDataSourceConfig(){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://116.62.188.148:3306/wx?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&AllowPublicKeyRetrieval=True");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("smf2020-");
        dsc.setDbType(DbType.MYSQL);
        return dsc;
    }

/*    public static DataSourceConfig getDataSourceConfig(){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:sqlserver://localhost:1433;DatabaseName=sync_data");
        dsc.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dsc.setUsername("SA");
        dsc.setPassword("Root@1234");
        dsc.setDbType(DbType.SQL_SERVER);
        return dsc;
    }*/


    /**
     * 设置全局配置
     * @return
     */
    public static GlobalConfig getGlobalConfig(){
        GlobalConfig gc = new GlobalConfig();
        String path = System.getProperty("user.dir");
        gc.setOutputDir(path+"/src/main/java");//参数是一个目录，所以需要获取当前系统目录
        gc.setAuthor("liuhao");
        gc.setOpen(true);//是否打开资源管理器
        gc.setFileOverride(true);//是否覆盖已经生成的
        gc.setIdType(IdType.INPUT);// id生成策略
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
//
//        gc.setEntityName("%sEntityMySQL");
//        gc.setMapperName("%sMapperMySQL");
//        gc.setXmlName("%sMapperMySQL");
//        gc.setServiceName("%sServiceMySQL");
//        gc.setServiceImplName("%sServiceImplMySQL");
//        gc.setControllerName("%sControllerMySQL");

        gc.setEntityName("%sEntitySQLserver");
        gc.setMapperName("%sMapperSQLserver");
        gc.setXmlName("%sMapperSQLserver");
        gc.setServiceName("%sServiceSQLserver");
        gc.setServiceImplName("%sServiceImplSQLserver");
        gc.setControllerName("%sControllerSQLserver");



//        gc.setEntityName("%sEntity");
//        gc.setMapperName("%sMapper");
//        gc.setXmlName("%sMapper");
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sServiceImpl");
//        gc.setControllerName("%sController");

        return gc;
    }

    /**
     *包配置
     * @return
     */
    public static PackageConfig getPackageInfo(){
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("datasynctask");
        pc.setParent("com.liuhao");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        return pc;
    }

    /**
     * 策略配置
     * @return
     */
    public static StrategyConfig getStrategyConfig(){
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 下划线转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 设置映射的表名，多张表
        strategy.setInclude("sys_users");

        strategy.setEntityLombokModel(true);// 是否启用lombok开启注解
        strategy.setLogicDeleteFieldName("isAction");//设置逻辑删除字段

        // 时间自动填充配置
        TableFill startDate = new TableFill("startDate", FieldFill.INSERT);
        TableFill updateDate = new TableFill("updateDate", FieldFill.UPDATE);

        ArrayList<TableFill> list = new ArrayList<>();
        list.add(startDate);
        list.add(updateDate);
        strategy.setTableFillList(list);



        // 乐观锁配置
        strategy.setVersionFieldName("version");
        // rustful 格式

        strategy.setRestControllerStyle(true);

        return  strategy;
    }


}
