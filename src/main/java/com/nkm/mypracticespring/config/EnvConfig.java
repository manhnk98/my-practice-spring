package com.nkm.mypracticespring.config;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class EnvConfig {
    public static final Long DB_CONNECTION_TIMEOUT;
    public static final Integer DB_MAX_POOL_SIZE;
    public static final Integer DB_MIN_IDLE;
    public static final String DB_PASSWORD;
    public static final String DB_DBAAS_URL;
    public static final String DB_AUTO_SCALE_URL;
    public static final String DB_USERNAME;

    // validate
    public static final Integer BK_MIN_KEEP_RECORD;
    public static final Integer BK_MAX_KEEP_RECORD;

    static {
        DB_CONNECTION_TIMEOUT = Long.valueOf(getValue("DB_CONNECTION_TIMEOUT", "100000"));
        DB_MAX_POOL_SIZE = Integer.valueOf(getValue("DB_MAX_POOL_SIZE", "30"));
        DB_MIN_IDLE = Integer.valueOf(getValue("DB_MIN_IDLE", "15"));
        DB_DBAAS_URL = getValue("DB_DBAAS_URL");
        DB_PASSWORD = getValue("DB_PASSWORD");
        DB_USERNAME = getValue("DB_USERNAME");
        DB_AUTO_SCALE_URL = getValue("DB_AUTO_SCALE_URL");

        BK_MIN_KEEP_RECORD = Integer.valueOf(getValue("BK_MIN_KEEP_RECORD", "1"));
        BK_MAX_KEEP_RECORD = Integer.valueOf(getValue("BK_MAX_KEEP_RECORD", "30"));
    }

    private static String getValue(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key)).orElse(defaultValue);
    }

    private static String getValue(String key) {
        String value = getValue(key, null);
        if (value == null) {
            log.error("env {} has not been assigned a value", key);
            System.out.println("env " + key + " has not been assigned a value");
        }

        return value;
    }

}
