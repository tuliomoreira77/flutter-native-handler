package com.example.flutternativehandler.exceptions;

public class MethodMapperException extends Exception {

    public MethodMapperException(String methodName) {
        super(String.format("Native method not found: %s", methodName));
    }
}
