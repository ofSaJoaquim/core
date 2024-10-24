package br.com.branetlogistica.core.log.auditory.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Configurable;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.context.ContextData;
import br.com.branetlogistica.core.interfaces.IEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Configurable
public class AuditListener  {
	

	@PrePersist
	protected void onCreate(Object object) {
		IEntity entity = (IEntity)object;
		ContextData contextData = Context.getContextData();
		entity.setCreatedAt(LocalDateTime.now());	
		entity.setVersion(1l);
		if (contextData != null)
			entity.setCreatedBy(contextData.getIdentity() != null ? contextData.getIdentity().getUserId() : null);
	}

	@PreUpdate
	protected void onUpdate(Object object) {
		IEntity entity = (IEntity)object;
		ContextData contextData = Context.getContextData();
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setVersion(entity.getVersion()+1l);
		if(contextData!=null)
			entity.setUpdatedBy(contextData.getIdentity()!=null?contextData.getIdentity().getUserId():null);
	}
	

	
}
