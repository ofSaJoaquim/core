package br.com.branetlogistica.core.exceptions.impl;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ApiException {

	private static final long serialVersionUID = 1L;

	public AlreadyExistsException(String messagem) {
		super(HttpStatus.CONFLICT, messagem);		
	}
	
	

}