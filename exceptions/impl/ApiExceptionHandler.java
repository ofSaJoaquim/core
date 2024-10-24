package br.com.branetlogistica.core.exceptions.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.context.ContextData;
import br.com.branetlogistica.core.exceptions.dto.ErrorMessage;
import br.com.branetlogistica.core.interceptor.PosInterceptor;




@RestControllerAdvice
public class ApiExceptionHandler {

	@Autowired
	private  PosInterceptor posInterceptor;
	
    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorMessage> handleApiException(ApiException ex) {
    	ContextData contextData = Context.getContextData();
        HttpStatus status = ex.getStatus();
        ErrorMessage errorDetails =
                ErrorMessage.builder()
                        .timestamp(ex.getTimestamp())
                        .status(status.value())
                        .message(ex.getMessage())
                        .errors(ex.getFields())
                        .path(contextData.getRequest().getUrl()+contextData.getRequest().getUri())              
                        .build();
        posInterceptor.posAction(status.value());
        return new ResponseEntity<>(errorDetails, status);
    }

}