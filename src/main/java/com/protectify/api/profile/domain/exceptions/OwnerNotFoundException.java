package com.protectify.api.profile.domain.exceptions;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(Long id) {
        super("Owner with id " + id + " not found");
    }
}