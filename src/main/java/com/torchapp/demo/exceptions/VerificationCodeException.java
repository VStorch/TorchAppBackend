package com.torchapp.demo.exceptions;

public class VerificationCodeException extends RuntimeException {
    public VerificationCodeException(String message) {
        super(message);
    }
}
