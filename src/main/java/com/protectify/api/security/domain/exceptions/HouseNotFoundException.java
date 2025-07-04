package com.protectify.api.security.domain.exceptions;

public class HouseNotFoundException extends RuntimeException {
    public HouseNotFoundException(Long id) {
        super("House with id " + id + " not found");
    }
}