package com.rajim.redis.config;

import com.rajim.redis.MainPackage;
import com.rajim.redis.settings.DbSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author rajim on 10/23/18
 */
@Slf4j
@Configuration(value = "redis-cacheDataConfig")
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackageClasses = MainPackage.class,
        entityManagerFactoryRef = "redis-cacheEMF", transactionManagerRef = "redis-cacheTM")
public class DataConfig extends AbstractDataConfig {

    private static final String PU_NAME = "redis-cache";
    private static final String DATASOURCE_NAME = "redis-cacheDataSource";
    private static final String[] PACKAGES = {MainPackage.class.getPackage().getName()};

    @Autowired
    @Bean(name = DATASOURCE_NAME)
    public DataSource adminDataSource(DbSettings redisCacheDbSettings) {
        log.info("DB Setting {}", redisCacheDbSettings);
        log.info(redisCacheDbSettings.toString());
        return buildDataSource(redisCacheDbSettings);
    }


    @Primary
    @Bean(name = "redis-cacheEMF")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier(value = DATASOURCE_NAME) DataSource dataSource) {
        return buildBackwardEntityManagerFactory(dataSource, PU_NAME, PACKAGES);
    }

    @Primary
    @Bean(name = "redis-cacheTM")
    public PlatformTransactionManager transactionManager(@Qualifier(value = "redis-cacheEMF") EntityManagerFactory emf) {
        return buildTransactionManager(emf);
    }

}
