package br.com.branetlogistica.core.tenant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.tenant.model.Tenant;

@Component
@ConfigurationPropertiesBinding
public class TenantPropertiesConverter implements Converter<String, Tenant> {
	
	@Value("${app.sso.url}")
	private String urlSSO;

	 public Tenant convert(String from) {
	        String[] data = from.split(",");
	        return new Tenant(data[0],data[1],urlSSO);
	    }

	
}
