package com.torchapp.demo.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Entidade não encontrada");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
