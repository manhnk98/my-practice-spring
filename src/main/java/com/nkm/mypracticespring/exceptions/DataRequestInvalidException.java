package com.nkm.mypracticespring.exceptions;

public class DataRequestInvalidException extends RuntimeException {
    public DataRequestInvalidException(String message) {
        super(message);
    }
}
