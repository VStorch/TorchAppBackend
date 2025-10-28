package com.torchapp.demo.exceptions;

public class MaxAttemptsExceededException extends RuntimeException {
    private final int maxAttempts;

    public MaxAttemptsExceededException(String message, int maxAttempts) {
        super(message);
        this.maxAttempts = maxAttempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
