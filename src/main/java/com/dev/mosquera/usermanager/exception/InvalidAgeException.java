package com.dev.mosquera.usermanager.exception;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String message) {
        super(message);
    }
    public InvalidAgeException(String message, Throwable cause) {}
}
