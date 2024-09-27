package com.nkm.mypracticespring.utils;

public class CommonUtils {

    public static String keySession(String userId, String sessionId) {
        return "session_" + userId + "_" + sessionId;
    }

}
