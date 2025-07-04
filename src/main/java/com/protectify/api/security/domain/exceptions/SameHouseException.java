package com.protectify.api.security.domain.exceptions;

public class SameHouseException extends RuntimeException {
    public SameHouseException(Long id) {
        super("La casa con id " + id + " ya se encuentra registrada.");
    }
}