package org.example.galacticmarketplace.service.exception;

import java.util.UUID;

public class CatNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Cat with id %s not found";
    public CatNotFoundException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}