package org.springframework.test.config;

import java.sql.Connection;

import javax.annotation.PreDestroy;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DaoConfig {
    private DataSource pool;
    
    @Bean(destroyMethod = "close")
    public javax.sql.DataSource dataSource() {
        pool = new DataSource();
        
        pool = new org.apache.tomcat.jdbc.pool.DataSource();        
        pool.setDriverClassName("com.mysql.jdbc.Driver");
        pool.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&noAccessToProcedureBodies=true&logger=org.springframework.test.config.MySqlLogger&profileSQL=true");
        pool.setUsername("root");
        pool.setPassword("");
        
        pool.setDefaultAutoCommit(true);
        pool.setInitialSize(10);
        pool.setMaxActive(100);
        pool.setMaxIdle(30);
        pool.setMinIdle(10);
        pool.setTestOnBorrow(true);
        pool.setTestOnReturn(false);
        pool.setTestWhileIdle(false);
        pool.setValidationQuery("select 1");
        // 验证间隔1小时
        pool.setValidationInterval(1 * 60 * 60 * 1000); 
        pool.setTimeBetweenEvictionRunsMillis(30000);
        pool.setMinEvictableIdleTimeMillis(30000);
        //调试使用
        pool.setRemoveAbandoned(true);
        pool.setRemoveAbandonedTimeout(60);
        pool.setLogAbandoned(true);
        pool.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //ConnectionProxy使用"=="比较字符串
        pool.setUseEquals(false);
        
        return pool;
    } 
    
    @PreDestroy
    public void close() {
        if(pool != null) {
            pool.close();
        }
    }
    
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(javax.sql.DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(javax.sql.DataSource dataSource){
        JdbcTemplate jtm = new JdbcTemplate();
        jtm.setDataSource(dataSource);
        return  jtm;
    }
}
