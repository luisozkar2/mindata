package com.challenge.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { NullPointerException.class})
	public ResponseEntity<Object> handleNullPointer(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "Hay un objeto null";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	
	@ExceptionHandler(value = { ResourceNotFoundException.class})
	public ResponseEntity<Object> handleNotFount(ResourceNotFoundException ex, WebRequest request) {
		String bodyOfResponse = "objeto no encontrado: " + ex.getClase() + " id: " +ex.getId() ;
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(value = { Exception.class})
	public ResponseEntity<Object> handleNoControlado(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "Error no controlado: " + ex.getMessage();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}