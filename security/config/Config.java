package br.com.branetlogistica.core.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class Config {


	@Bean
	public JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter() {
		return new JwtGrantedAuthoritiesConverter();
	}

	
	

}
