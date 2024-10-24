package br.com.branetlogistica.core.log.access.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import br.com.branetlogistica.core.context.ContextData;
import br.com.branetlogistica.core.log.dto.IdentityLog;
import br.com.branetlogistica.core.log.dto.RequestLog;
import lombok.Getter;

@Getter
public class AccessLogDTO {

	private final IdentityLog identity;
	private final RequestLog request;
	private final Long duration;	
	private final Integer status;
	
	public AccessLogDTO(ContextData contextData, Integer status) {
		super();
		if(contextData.getIdentity()!=null)
			this.identity =  new IdentityLog(contextData.getIdentity());
		else
			this.identity = null;
		this.request = new RequestLog(contextData.getRequest());
		this.duration = ChronoUnit.MILLIS.between(contextData.getRequest().getDateEvent(), LocalDateTime.now());
		this.status = status;
	}
	
	
	
}
