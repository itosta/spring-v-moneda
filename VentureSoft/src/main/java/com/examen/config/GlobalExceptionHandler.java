package com.examen.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.examen.exception.EmpleadoNotFoundException;
import com.examen.exception.MonedaNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
	
    @ExceptionHandler(MonedaNotFoundException.class)
    public ResponseEntity<String> handleMonedaNotFound(MonedaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    
    @ExceptionHandler(EmpleadoNotFoundException.class)
    public ResponseEntity<String> handleEmpleadoNotFound(EmpleadoNotFoundException e){
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}