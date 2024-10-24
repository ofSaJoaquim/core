package br.com.branetlogistica.core.exceptions.impl;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.branetlogistica.core.exceptions.dto.ErrorMessage;
import br.com.branetlogistica.core.exceptions.dto.FieldErrorDto;
import br.com.branetlogistica.core.interceptor.PosInterceptor;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

	private static final ZoneId utc = ZoneId.of("UTC");

	@Autowired
	private  PosInterceptor posInterceptor;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {	
		
		List<FieldErrorDto>errors = new ArrayList<FieldErrorDto>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			
			String fieldName = ((FieldError) error).getField();
			String fieldValue = ((FieldError) error).getRejectedValue()!=null? ((FieldError) error).getRejectedValue().toString():null;
			
			errors.add(new FieldErrorDto(fieldName, error.getDefaultMessage(), fieldValue));
			
		});
		ErrorMessage error= ErrorMessage.builder()
				.errors(errors)
				.message("Erro de validação")
				.timestamp(OffsetDateTime.now(utc))
				.path(request.getContextPath())
				.status(HttpStatus.BAD_REQUEST.value())
				.build();
		posInterceptor.posAction(status.value());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
}
