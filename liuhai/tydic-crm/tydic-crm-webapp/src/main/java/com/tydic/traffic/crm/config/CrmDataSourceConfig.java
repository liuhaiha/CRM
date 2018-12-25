package com.tydic.traffic.crm.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.tydic.traffic.tafa.daf.mybatis.tex.MapperScannerConfigurer;
import com.tydic.traffic.tafa.daf.page.dialect.DialectType;
import com.tydic.traffic.tafa.daf.page.interceptor.PageInterceptor;
import com.tydic.traffic.tafa.daf.page.starter.PageProperties;




/**
 * 主数据源的配置
 *
 * @author acer
 */
@Configuration
@EnableConfigurationProperties(PageProperties.class)
public class CrmDataSourceConfig {
	
	// 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.tydic.traffic.crm.dao";
    static final String MAPPER_LOCATION = "classpath:mapper/crm/**/*.xml";
    static final String SESSION_FACTORY_BEAN_NAME="crmSqlSessionFactory";

	@Primary
	@Bean(name = "crmDataSource",initMethod="init")
	@ConfigurationProperties(prefix="crm.datasource")
	public DruidDataSource druidDataSource() {
        return new DruidDataSource();
	}

	@Bean(name = "crmTransactionManager")
    @Primary
    public DataSourceTransactionManager crmTransactionManager(@Qualifier("crmDataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }

    /**
     *
     * @param masterDataSource
     * @param pageProperties
     * @return
     * @throws Exception
     */
	@Bean(name = "crmSqlSessionFactory")
    @Primary
    public SqlSessionFactory crmSqlSessionFactory(@Qualifier("crmDataSource") DataSource masterDataSource,
               @Autowired PageProperties pageProperties) throws Exception {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialect", DialectType.MYSQL.getDialectName());
        properties.setProperty("count.isCount", String.valueOf(pageProperties.getCount().isCount()));
        properties.setProperty("count.expireAfterAccess", String.valueOf(pageProperties.getCount().getExpireAfterAccess()));
        properties.setProperty("count.maximumSize", String.valueOf(pageProperties.getCount().getMaximumSize()));
        pageInterceptor.setProperties(properties);
        
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
        

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setPlugins(new Interceptor[] { pageInterceptor });
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(CrmDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name="crmMapperScannerConfigurer")
    public MapperScannerConfigurer masterMapperScannerConfigurer(){
        Properties properties=new Properties();
        properties.put("mappers","com.tydic.traffic.tafa.daf.mybatis.tk.common.Mapper");
        properties.setProperty("ORDER", "BEFORE");

        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.getMapperHelper().setProperties(properties);
        msc.setSqlSessionFactoryBeanName(CrmDataSourceConfig.SESSION_FACTORY_BEAN_NAME);
        msc.setBasePackage(CrmDataSourceConfig.PACKAGE);

        return msc;
    }

}