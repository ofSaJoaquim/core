package br.com.branetlogistica.core.tenant.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import br.com.branetlogistica.core.tenant.model.Tenant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

	private  List<Tenant> tenants = new ArrayList<Tenant>();
	
}
