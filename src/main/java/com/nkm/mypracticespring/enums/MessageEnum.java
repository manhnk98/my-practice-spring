package com.nkm.mypracticespring.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MessageEnum {
    SUCCESS("20001", "Success"),
    ERR_INVALID_TOKEN("30001", "Token is invalid");

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
