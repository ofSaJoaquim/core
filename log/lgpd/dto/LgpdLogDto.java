package br.com.branetlogistica.core.log.lgpd.dto;

import java.util.List;

import br.com.branetlogistica.core.context.ContextData;
import br.com.branetlogistica.core.log.dto.IdentityLog;
import br.com.branetlogistica.core.log.dto.RequestLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LgpdLogDto  {
	
	private final IdentityLog identity;
	private final RequestLog request;
	private final List<Long>peopleIds;
	private final List<String> peopleFields;
	public LgpdLogDto(ContextData contextData, List<Long> peopleIds, List<String> peopleFields) {
		super();
		if(contextData.getIdentity()!=null)
			this.identity =  new IdentityLog(contextData.getIdentity());
		else
			this.identity = null;
		this.request = new RequestLog(contextData.getRequest());
		this.peopleIds = peopleIds;
		this.peopleFields = peopleFields;
	}
	
	

}
