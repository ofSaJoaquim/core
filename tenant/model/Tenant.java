package br.com.branetlogistica.core.tenant.model;

import java.util.Map;

import br.com.branetlogistica.core.security.tenant.OidcProviderConfigurationUtils;
import lombok.Getter;

@Getter
public class Tenant {

	private final String clientId;
	private final String legancyUrl;
	private final Map<String, Object> attributes;

	public Tenant(String clientId, String legancyUrl, String ssoUrl) {
		super();
		this.clientId = clientId;
		this.legancyUrl = legancyUrl;
		this.attributes = OidcProviderConfigurationUtils.getConfigurationForOidcIssuerLocation(ssoUrl+"/realms/"+clientId);
	}
	
	public Object getAttribute(String attribute) {
        return attributes.get(attribute);
    }
	
}
