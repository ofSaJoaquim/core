package br.com.branetlogistica.core.security.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.security.client.dto.ClientToken;
import br.com.branetlogistica.core.security.client.dto.ResponseToken;
import br.com.branetlogistica.core.tenant.model.Tenant;
import br.com.branetlogistica.core.tenant.service.TenantService;

@Component
public class SecurityClient {

	private final static Map<String, ClientToken> TOKENS = new HashMap<String, ClientToken>();
	private final String tokenUri = "/protocol/openid-connect/token";

	@Value("${app.sso.client.id}")
	private String clientId;

	@Value("${app.sso.client.secret}")
	private String clientPassword;

	@Value("${app.sso.url}")
	private String clientUrl;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private RestTemplate restTemplate;

	private ClientToken loadServiceAuthToken(Tenant tenant) {

		String uri = clientUrl + "/realms/" + tenant.getClientId() + tokenUri;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("client_secret", clientPassword);
		map.add("grant_type", "client_credentials");
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<ResponseToken> response = restTemplate.postForEntity(uri, entity, ResponseToken.class);
		ResponseToken responseToken = response.getBody();
		Long expiresAt = responseToken.getExpiresIn() + System.currentTimeMillis();
		ClientToken clientToken = new ClientToken(responseToken, expiresAt);

		if (TOKENS.containsKey(tenant.getClientId())) {
			TOKENS.replace(tenant.getClientId(), clientToken);
		} else
			TOKENS.put(tenant.getClientId(), clientToken);

		return clientToken;

	}

	public String getResponseToken() {
		String tenantId = Context.getContextData().getRequest().getClientId();
		Tenant tenant = tenantService.findTenantByClientId(tenantId);
		ClientToken clientToken = null;
		if (TOKENS.containsKey(tenantId))
			clientToken = TOKENS.get(tenantId);

		if (clientToken == null || clientToken.getExpiresAt() < System.currentTimeMillis())
			clientToken = loadServiceAuthToken(tenant);

		return "Bearer " + clientToken.getResponseToken().getAccessToken();

	}

}
