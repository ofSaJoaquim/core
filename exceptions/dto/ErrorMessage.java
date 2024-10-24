package br.com.branetlogistica.core.exceptions.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorMessage {

  @JsonProperty("timestamp")  
  private OffsetDateTime timestamp;

  @JsonProperty("status") 
  private Integer status;

  @JsonProperty("message")  
  private String message;

  @JsonProperty("path")
  private String path;
    
  @JsonProperty("erros")
  private List<FieldErrorDto> errors;
  
 

}

