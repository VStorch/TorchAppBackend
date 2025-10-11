package com.torchapp.demo.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entidade não encontrada");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
