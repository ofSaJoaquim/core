package br.com.branetlogistica.core.exceptions.impl;

import org.springframework.http.HttpStatus;

public class InternalException extends ApiException {

	private static final long serialVersionUID = 1L;
	 public InternalException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR,"Erro interno do servidor");
    }
}