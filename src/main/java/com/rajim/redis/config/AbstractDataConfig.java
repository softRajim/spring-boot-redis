package com.rajim.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rajim on 10/23/18
 */
@Slf4j
@EnableTransactionManagement
public abstract class AbstractDataConfig {

    private static final String QUERY = "SELECT 1";
    private static boolean OLD_CONVENTION = false;

    private static final String DB_DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect";
    private static final String SLOW_QUERY_REPORT = "org.apache.tomcat.jdbc.pool.interceptor.SlowQueryReportJmx(threshold=10000)";
    private static final String CONNECTION_STATE = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState";
    private static final String STATEMENT_FINALIZER = "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";
    private static final String RESET_ABANDON_TIMER = "org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer";

    protected DataSource buildDataSource(AbstractDBSetting dbSetting) {
        log.info("Building datasource for {}", dbSetting.toString());

        final PoolProperties props = new PoolProperties();
        props.setUrl(dbSetting.getUrl());
        props.setDriverClassName(dbSetting.getDatabaseDriverName());
        props.setUsername(dbSetting.getUsername());
        props.setPassword(dbSetting.getPassword());

        props.setTestOnBorrow(dbSetting.isTestOnBorrow());
        props.setValidationQuery(QUERY);
        props.setMaxWait(dbSetting.getMaxWait());
        props.setMaxActive(dbSetting.getMaxPool());

        props.setRemoveAbandoned(dbSetting.isRemoveAbandon());
        props.setRemoveAbandonedTimeout(dbSetting.getAbandonTimeout());
        props.setLogAbandoned(dbSetting.isLogAbandon());

        props.setJdbcInterceptors(CONNECTION_STATE + ";" + STATEMENT_FINALIZER + ";" + RESET_ABANDON_TIMER + ";" + SLOW_QUERY_REPORT);
        final org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setPoolProperties(props);
        return dataSource;
    }

    /**
     * <p>Naming strategy defines how table and it's column name will be structured.</p>
     *
     * @return Properties
     */
    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", DB_DIALECT);
        props.put("hibernate.physical_naming_strategy", OLD_CONVENTION ? OldNamingConvention.class.getName() : DefaultNamingStrategy.class.getName());
        props.put("hibernate.id.new_generator_mappings", "false");
        return props;
    }

    protected LocalContainerEntityManagerFactoryBean buildBackwardEntityManagerFactory(
            DataSource dataSource,
            String persistenceName,
            String... packages) {
        this.OLD_CONVENTION = true;
        return buildEntityManagerFactory(dataSource, persistenceName, packages);
    }

    protected LocalContainerEntityManagerFactoryBean buildEntityManagerFactory(DataSource dataSource,
                                                                               String persistenceName,
                                                                               String... packages) {
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(packages);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPersistenceUnitName("pu-" + persistenceName);
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties());
        return entityManagerFactoryBean;
    }

    protected PlatformTransactionManager buildTransactionManager(EntityManagerFactory factoryBean) {
        return new JpaTransactionManager(factoryBean);
    }
}
