package br.com.branetlogistica.core.exceptions.impl;

import org.springframework.http.HttpStatus;

public class ForbidenException extends ApiException {

    private static final long serialVersionUID = 1L;
    
    public ForbidenException(String messagem) {
  		super(  HttpStatus.FORBIDDEN, messagem);
  	}
  	

}