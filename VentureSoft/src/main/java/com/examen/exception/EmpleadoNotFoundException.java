package com.examen.exception;

@SuppressWarnings("serial")
public class EmpleadoNotFoundException extends RuntimeException{
	
	public EmpleadoNotFoundException(String message) {
		super(message);
	}

}
