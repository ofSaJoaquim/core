package br.com.branetlogistica.core.interfaces;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class DefaultResponse<T>  {
	  
	
	  @JsonProperty("timestamp")  
	  private OffsetDateTime timestamp;

	  @JsonProperty("status") 
	  private Integer status;
	  
	  @JsonProperty("messagem")  
	  private String messagem;
	  
	  @JsonProperty("response")
	  private T response;
	  
	  
	  @JsonProperty("resources")  
	  private List<ResourceItem> resources;

	  

	
	public ResponseEntity<DefaultResponse<T>> createResponse(){
		return new ResponseEntity<DefaultResponse<T>>(this, HttpStatusCode.valueOf(this.status));
	}

	public DefaultResponse() {
		super();
		this.timestamp = OffsetDateTime.now(ZoneId.of("UTC"));
		
	}

	public DefaultResponse(HttpStatusCode status, String messagem, List<ResourceItem> resources,T response) {
		super();
		this.timestamp = OffsetDateTime.now(ZoneId.of("UTC"));
		this.status = status.value();
		this.messagem = messagem;
		this.resources = resources;
		this.response = response;
	}
	
	

}
