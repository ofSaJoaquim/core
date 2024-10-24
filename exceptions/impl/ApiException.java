package br.com.branetlogistica.core.exceptions.impl;


import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.http.HttpStatus;

import br.com.branetlogistica.core.exceptions.dto.FieldErrorDto;
import lombok.Getter;


@Getter
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final ZoneId utc = ZoneId.of("UTC");
    private final OffsetDateTime timestamp;
    private final HttpStatus status;
    private final List<FieldErrorDto> fields;
          
    public ApiException(HttpStatus status, String mensagem) {
    	super(mensagem);
        this.timestamp = OffsetDateTime.now(utc);
        this.status = status;       
        this.fields = null;   
    }
    
    public ApiException(HttpStatus status, String mensagem, List<FieldErrorDto> fields) {
        super(mensagem);
        this.timestamp = OffsetDateTime.now(utc);
        this.status = status;       
        this.fields =  fields;           
    }	
    
  
   
    
	
    
}