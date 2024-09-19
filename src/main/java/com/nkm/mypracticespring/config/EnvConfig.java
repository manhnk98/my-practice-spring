package com.nkm.mypracticespring.config;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class EnvConfig {
    public static final Long DB_POSTRGESQL_CONNECTION_TIMEOUT;
    public static final Integer DB_POSTRGESQL_MAX_POOL_SIZE;
    public static final Integer DB_POSTRGESQL_MIN_IDLE;
    public static final String DB_POSTRGESQL_URL;
    public static final String DB_POSTRGESQL_PASSWORD;
    public static final String DB_POSTRGESQL_USERNAME;

    public static final Long DB_MYSQL_CONNECTION_TIMEOUT;
    public static final Integer DB_MYSQL_MAX_POOL_SIZE;
    public static final Integer DB_MYSQL_MIN_IDLE;
    public static final String DB_MYSQL_URL;
    public static final String DB_MYSQL_PASSWORD;
    public static final String DB_MYSQL_USERNAME;

    static {
        DB_POSTRGESQL_CONNECTION_TIMEOUT = Long.valueOf(getValue("DB_POSTRGESQL_CONNECTION_TIMEOUT", "100000"));
        DB_POSTRGESQL_MAX_POOL_SIZE = Integer.valueOf(getValue("DB_POSTRGESQL_MAX_POOL_SIZE", "30"));
        DB_POSTRGESQL_MIN_IDLE = Integer.valueOf(getValue("DB_POSTRGESQL_MIN_IDLE", "15"));
        DB_POSTRGESQL_URL = getValue("DB_POSTRGESQL_URL", "jdbc:postgresql://localhost:5432/test_database");
        DB_POSTRGESQL_USERNAME = getValue("DB_POSTRGESQL_USERNAME", "postgres");
        DB_POSTRGESQL_PASSWORD = getValue("DB_POSTRGESQL_PASSWORD", "admin");

        DB_MYSQL_CONNECTION_TIMEOUT = Long.valueOf(getValue("DB_MYSQL_CONNECTION_TIMEOUT", "100000"));
        DB_MYSQL_MAX_POOL_SIZE = Integer.valueOf(getValue("DB_MYSQL_MAX_POOL_SIZE", "30"));
        DB_MYSQL_MIN_IDLE = Integer.valueOf(getValue("DB_MYSQL_MIN_IDLE", "15"));
        DB_MYSQL_URL = getValue("DB_MYSQL_URL", "jdbc:mysql://localhost:3306/test_database");
        DB_MYSQL_USERNAME = getValue("DB_MYSQL_USERNAME", "root");
        DB_MYSQL_PASSWORD = getValue("DB_MYSQL_PASSWORD", "admin");
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
