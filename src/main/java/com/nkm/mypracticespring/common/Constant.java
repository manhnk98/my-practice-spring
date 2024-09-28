package com.nkm.mypracticespring.common;

import java.time.Duration;

public class Constant {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String SESSION_ID_REQUEST = "sessionId";
    public static final String SHOP_CONTEXT = "shopContext";
    public static final String SUB_JWT = "sub";
    public static final String JTI_JWT = "jti";
    public static final String PAYLOAD_USER_ID = "userId";
    public static final String PAYLOAD_EMAIL = "email";

    public static final Duration ACCESS_TOKEN_EXPIRE_TIME = Duration.ofMinutes(5);
    public static final Duration REFRESH_TOKEN_EXPIRE_TIME = Duration.ofMinutes(60);

}
