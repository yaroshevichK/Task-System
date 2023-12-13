package com.example.testtask.exception;

public class NotExistsInDBException extends RuntimeException {
    public NotExistsInDBException(String message) {
        super(message);
    }
}
