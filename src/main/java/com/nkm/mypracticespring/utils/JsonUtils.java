package com.nkm.mypracticespring.utils;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class JsonUtils {

    @SneakyThrows
    public static <T> T toObject(String jsonStr, Class<T> zClass) {
        try {
            if (isValidJson(jsonStr)) {
                return AppUtils.objectMapper().readValue(jsonStr, zClass);
            }
        } catch (Exception e) {
            log.error("toObject ERROR => json[{}] - toClass[{}] - message[{}]", jsonStr, zClass.getName(), e.getMessage());
        }

        return null;
    }

    public static <T> T toObject(Map<String, Object> value, Class<T> zClass) {
        try {
            return AppUtils.objectMapper().convertValue(value, zClass);
        } catch (Exception e) {
            log.error("toObject ERROR mapObject[{}] - toClass[{}] - message[{}]", value, zClass.getName(), e.getMessage());
            return null;
        }
    }

    public static boolean isValidJson(String jsonString) {
        try {
            AppUtils.objectMapper().readTree(jsonString);
            return true;
        } catch (Exception e) {
            log.error("isValidJson ERROR => json[{}]", e.getMessage());
            return false;
        }
    }

}
