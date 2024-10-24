package br.com.branetlogistica.core.exceptions.impl;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    private static final long serialVersionUID = 1L;  
    
    public NotFoundException(String messagem) {
 		super(HttpStatus.NOT_FOUND, messagem); 			
 	}
 	
 	
}