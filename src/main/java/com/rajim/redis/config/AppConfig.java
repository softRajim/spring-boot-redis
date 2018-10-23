package com.rajim.redis.config;

import com.rajim.redis.MainPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author rajim on 10/23/18
 */
@Import({DataConfig.class})
@ComponentScan(basePackageClasses = {MainPackage.class})
@EnableAsync
@EnableScheduling
public class AppConfig {
}

