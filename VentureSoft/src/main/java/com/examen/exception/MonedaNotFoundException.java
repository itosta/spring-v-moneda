package com.examen.exception;

@SuppressWarnings("serial")
public class MonedaNotFoundException extends RuntimeException{
	
    public MonedaNotFoundException(String message) {
        super(message);
    }
}