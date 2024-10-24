package br.com.branetlogistica.core.security.tenant;


import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.branetlogistica.core.client.CustomRestTemplateConfiguration;

/**
 * stolen from org.springframework.security.oauth2.jwt.JwtDecoderProviderConfigurationUtils
 */
public class OidcProviderConfigurationUtils {

    private static final String OIDC_METADATA_PATH = "/.well-known/openid-configuration";

    private static final RestTemplate rest =  CustomRestTemplateConfiguration.restTemplate();

    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP =
            new ParameterizedTypeReference<>() {};

    private OidcProviderConfigurationUtils(){}

    public static Map<String, Object> getConfigurationForOidcIssuerLocation(String oidcIssuerLocation) {
        return getConfiguration(oidcIssuerLocation, oidc(URI.create(oidcIssuerLocation)));
    }

    private static URI oidc(URI issuer) {
        // @formatter:off
        return UriComponentsBuilder.fromUri(issuer)
                .replacePath(issuer.getPath() + OIDC_METADATA_PATH)
                .build(Collections.emptyMap());
        // @formatter:on
    }

    private static Map<String, Object> getConfiguration(String issuer, URI... uris) {
        String errorMessage = "Unable to resolve the Configuration with the provided Issuer of " + "\"" + issuer + "\"";
        for (URI uri : uris) {
            try {
                RequestEntity<Void> request = RequestEntity.get(uri).build();
                ResponseEntity<Map<String, Object>> response = rest.exchange(request, STRING_OBJECT_MAP);
                Map<String, Object> configuration = response.getBody();
                Assert.isTrue(configuration.get("jwks_uri") != null, "The public JWK set URI must not be null");
                return configuration;
            }
            catch (IllegalArgumentException ex) {
            	ex.printStackTrace();
                throw ex;
            }
            catch (RuntimeException ex) {
                if (!(ex instanceof HttpClientErrorException
                        && ((HttpClientErrorException) ex).getStatusCode().is4xxClientError())) {
                	ex.printStackTrace();
                    throw new IllegalArgumentException(errorMessage, ex);
                }
                // else try another endpoint
            }
        }
        throw new IllegalArgumentException(errorMessage);
    }
    
    
}