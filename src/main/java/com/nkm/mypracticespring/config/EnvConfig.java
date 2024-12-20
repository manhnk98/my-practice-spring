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

    public static final String MONGODB_PASSWORD;
    public static final String MONGODB_USERNAME;
    public static final String MONGODB_HOST;
    public static final int MONGODB_PORT;
    public static final String MONGODB_DATABASE_NAME;

    public static final String JWT_SECRET;

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

//        MONGODB_CONNECTION_TIMEOUT = getValue("MONGODB_CONNECTION_TIMEOUT", "10");
//        MONGODB_MAX_POOL_SIZE = getValue();
//        MONGODB_MIN_IDLE = getValue();

        MONGODB_USERNAME = getValue("MONGODB_USERNAME", "root");
        MONGODB_PASSWORD = getValue("MONGODB_PASSWORD", "admin");
        MONGODB_HOST = getValue("MONGODB_HOST", "localhost");
        MONGODB_PORT = Integer.parseInt(getValue("MONGODB_PORT", "27017"));
        MONGODB_DATABASE_NAME = getValue("MONGODB_DATABASE_NAME", "e-commerce");

        JWT_SECRET = getValue("JWT_SECRET", "5pAq6zRyX8bC3dV2wS7gN1mK9jF0hL4tUoP6iBvE3nG8xZaQrY7cW2fA1998ManhNK");
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
