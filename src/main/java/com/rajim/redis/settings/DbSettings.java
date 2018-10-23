package com.rajim.redis.settings;

import com.rajim.redis.config.AbstractDBSetting;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author rajim on 10/23/18
 */
@Component
@ConfigurationProperties("datasource.redis-cache")
public class DbSettings extends AbstractDBSetting {

}
