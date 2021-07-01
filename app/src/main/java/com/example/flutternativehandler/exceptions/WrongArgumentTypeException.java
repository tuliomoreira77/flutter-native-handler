package com.example.flutternativehandler.exceptions;

public class WrongArgumentTypeException extends Exception {

    public WrongArgumentTypeException(String argumentType) {
        super(String.format("Error parsing argument, check if types are compatible. Expected type: %s", argumentType));
    }
}
