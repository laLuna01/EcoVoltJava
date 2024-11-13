package com.example.ecovoltjava.exception;

public class TimeoutException extends RuntimeException {
    public TimeoutException(String message) {
        super(message);
    }
}
