package com.torchapp.demo.exceptions;

public class InvalidVerificationCodeException extends RuntimeException {
    private final int remainingAttempts;

    public InvalidVerificationCodeException(String message, int remainingAttempts) {
        super(message);
        this.remainingAttempts = remainingAttempts;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }
}
