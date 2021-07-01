package com.example.flutternativehandler.exceptions;

public class ParsingException extends Exception{

    public ParsingException() {
        super("Error parsing the response, check if can be serialized");
    }

}
