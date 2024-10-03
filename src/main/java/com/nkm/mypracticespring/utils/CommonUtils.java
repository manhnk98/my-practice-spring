package com.nkm.mypracticespring.utils;

import com.github.slugify.Slugify;

import java.math.BigDecimal;

public class CommonUtils {

    public static String keySession(String userId, String sessionId) {
        return "session_" + userId + "_" + sessionId;
    }

    public static boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInteger(String input) {
        if (input == null || input.isEmpty() || !isNumeric(input)) {
            return false;
        }
        BigDecimal bd = new BigDecimal(input);
        return bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    public static String toSlug(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        return Slugify.builder().build().slugify(str.toLowerCase());
    }

}
