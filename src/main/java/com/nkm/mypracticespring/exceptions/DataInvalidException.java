package com.nkm.mypracticespring.exceptions;

public class DataInvalidException extends RuntimeException {
    public DataInvalidException(String message) {
        super(message);
    }
}
