package com.nkm.mypracticespring.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MessageEnum {
    SUCCESS("20001", "Success"),

    ERR_APPLICATION("30000", "Application error"),
    ERR_INVALID_TOKEN("30001", "Token is invalid"),
    ERR_INVALID_PARAM("30002", "Param invalid"),
    ERR_UNAUTHORIZED("30003", "Unauthorized"),;

    private final String code;
    private final String message;

    MessageEnum(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static MessageEnum getByCode(String code) {
        return Arrays.stream(MessageEnum.values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }

    public static MessageEnum getByCodeOrElseThrow(String code) {
        MessageEnum messageEnum = getByCode(code);
        if (messageEnum == null) {
            throw new ArrayIndexOutOfBoundsException("");
        }
        return messageEnum;
    }
}
