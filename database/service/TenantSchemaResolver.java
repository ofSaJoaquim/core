package br.com.branetlogistica.core.database.service;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.context.Context;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver<String> {

	@Override
	public String resolveCurrentTenantIdentifier() {
		if (Context.getContextData() == null)
			return "branet";
		String context = Context.getContextData().getRequest().getClientId();
		return context;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
