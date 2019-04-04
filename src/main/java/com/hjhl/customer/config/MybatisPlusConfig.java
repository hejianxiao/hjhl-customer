package com.hjhl.customer.config;

import com.baomidou.mybatisplus.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建人: Hjx
 * Date: 2018/6/13
 * Description:
 */
@Configuration
@MapperScan("com.hjhl.customer.mapper*")
public class MybatisPlusConfig {

    /**
     * 注入主键生成器
     */
    @Bean
    public IKeyGenerator keyGenerator(){
        return new H2KeyGenerator();
    }

    /**
     * 注入sql注入器
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


//    @Bean(name = "db1")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.db1" )
//    public DataSource db1 () {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "db2")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.db2" )
//    public DataSource db2 () {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "db3")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.db3" )
//    public DataSource db3 () {
//        return DruidDataSourceBuilder.create().build();
//    }

//    /**
//     * 动态数据源配置
//     * @return
//     */
//    @Bean
//    @Primary
//    public DataSource multipleDataSource (@Qualifier("db1") DataSource db1,
//                                          @Qualifier("db2") DataSource db2,
//                                          @Qualifier("db3") DataSource db3) {
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        Map< Object, Object > targetDataSources = new HashMap<>();
//        targetDataSources.put(DBTypeEnum.db1.getValue(), db1 );
//        targetDataSources.put(DBTypeEnum.db2.getValue(), db2);
//        targetDataSources.put(DBTypeEnum.db3.getValue(), db3);
//        dynamicDataSource.setTargetDataSources(targetDataSources);
//        dynamicDataSource.setDefaultTargetDataSource(db1);
//        return dynamicDataSource;
//    }


//    @Bean
//    public GlobalConfiguration globalConfiguration() {
//        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
//        conf.setLogicDeleteValue("-1");
//        conf.setLogicNotDeleteValue("1");
//        conf.setIdType(0);
////        conf.setMetaObjectHandler(new MyMetaObjectHandler());
//        conf.setDbColumnUnderline(true);
//        conf.setRefresh(true);
//        return conf;
//    }


}
