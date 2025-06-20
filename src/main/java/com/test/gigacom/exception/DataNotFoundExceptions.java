package com.test.gigacom.exception;

public class DataNotFoundExceptions extends RuntimeException {
    public DataNotFoundExceptions(String message) {
        super(message);
    }

    public DataNotFoundExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
