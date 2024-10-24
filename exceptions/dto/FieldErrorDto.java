package br.com.branetlogistica.core.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorDto {
	
	private String field;
	private String messagem;
	private String value;

}
