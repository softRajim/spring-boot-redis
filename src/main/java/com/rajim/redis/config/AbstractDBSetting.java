package com.rajim.redis.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author rajim on 10/23/18
 */
@Getter
@Setter
@ToString
public abstract class AbstractDBSetting {

    private String databaseDriverName;

    private String url;

    private String username;

    private String password;

    /*
        Default value is set and they can be overridden by child class
     */
    /**
     * The maximum number of connection that a database pool will create. Default is 50.
     */
    private int maxPool = 100;

    private int maxWait = 60000;

    private int abandonTimeout = 120;

    private boolean logAbandon = true;

    private boolean removeAbandon = true;

    private boolean testOnBorrow = true;
}
