package com.protectify.api.security.domain.exceptions;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(Long id) {
        super("Device with id " + id + " not found");
    }
}