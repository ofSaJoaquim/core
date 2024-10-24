package br.com.branetlogistica.core.log.auditory.dto;

import java.util.List;

import br.com.branetlogistica.core.context.ContextData;
import br.com.branetlogistica.core.log.auditory.enums.LogAuditoryEventType;
import br.com.branetlogistica.core.log.auditory.interfaces.ILogAuditoryEvent;
import br.com.branetlogistica.core.log.dto.IdentityLog;
import br.com.branetlogistica.core.log.dto.RequestLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AuditoryLogDto {

	private final IdentityLog identity;
	private final RequestLog request;
	private final String entityName;
	private final List<String> oldValues;
	private final List<String> newValues;
	private final List<String> attributes;
	private final LogAuditoryEventType type;
	private final String entityId;
	public AuditoryLogDto(ContextData contextData, ILogAuditoryEvent auditoryEvent,
			List<String> oldValues, List<String> newValues, List<String> attributes) {
		super();
		if(contextData.getIdentity()!=null)
			this.identity =  new IdentityLog(contextData.getIdentity());
		else
			this.identity = null;
		this.request = new RequestLog(contextData.getRequest());
		
		this.entityName = auditoryEvent.getEntityName();
		this.oldValues = oldValues;
		this.newValues = newValues;
		this.attributes = attributes;
		this.type = auditoryEvent.getType();
		this.entityId = auditoryEvent.getId().toString();
	}
	

	

	

}