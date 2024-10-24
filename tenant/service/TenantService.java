package br.com.branetlogistica.core.tenant.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import br.com.branetlogistica.core.exceptions.impl.NotFoundException;
import br.com.branetlogistica.core.tenant.config.ApplicationProperties;
import br.com.branetlogistica.core.tenant.model.Tenant;


@Service
@EnableConfigurationProperties(ApplicationProperties.class)
public class TenantService {

	@Autowired
	private ApplicationProperties applicationProperties;
	
	
	public Tenant findTenantByClientId(String clientId) {
		Optional<Tenant> obj = 
				applicationProperties.getTenants()
				 .stream()
				 .filter(x -> x.getClientId().equals(clientId))
				 .findFirst();
		
		return obj.orElseThrow(() -> new NotFoundException("Não foi encontrado tenant com o cliente_id = "+clientId));
		
		
	}
	
	public List<Tenant> findAll(){
		return applicationProperties.getTenants();
	}
	
}
