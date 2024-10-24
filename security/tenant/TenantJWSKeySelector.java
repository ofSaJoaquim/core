package br.com.branetlogistica.core.security.tenant;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.JWTClaimsSetAwareJWSKeySelector;

import br.com.branetlogistica.core.client.CustomRestTemplateConfiguration;
import br.com.branetlogistica.core.tenant.service.TenantService;

@Component
public class TenantJWSKeySelector
    implements JWTClaimsSetAwareJWSKeySelector<SecurityContext> {

    private final TenantService tenantService;
    private final Map<String, JWSKeySelector<SecurityContext>> selectors = new ConcurrentHashMap<>();

    public TenantJWSKeySelector(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Override
    public List<? extends Key> selectKeys(JWSHeader jwsHeader, JWTClaimsSet jwtClaimsSet, SecurityContext securityContext)
            throws KeySourceException {
        return this.selectors.computeIfAbsent(toTenant(jwtClaimsSet), this::fromTenant)
                .selectJWSKeys(jwsHeader, securityContext);
    }

    private String toTenant(JWTClaimsSet claimSet) {
        return (String) claimSet.getClaim("CLIENT-ID");
    }

    
    //busca se não tiver na salvo
    private JWSKeySelector<SecurityContext> fromTenant(String tenant) {
    	/*if(this.tenantRepository.findById(tenant) == null)
    		this.tenantRepository.save(new Tenant(tenant));*/
    	
    	
        return Optional.ofNullable(this.tenantService.findTenantByClientId(tenant))
                .map(t -> (String)t.getAttribute("jwks_uri"))
                .map(this::fromUri)
                .orElseThrow(() -> new IllegalArgumentException("unknown tenant"));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private JWSKeySelector<SecurityContext> fromUri(String uri) {
        try {
        	String retorno = CustomRestTemplateConfiguration.restTemplate().getForObject(uri, String.class);
        	InputStream targetStream = new ByteArrayInputStream(retorno.getBytes());
        	//new JwkSetUriJwtDecoderBuilder().restOperations(CustomRestTemplateConfiguration.restTemplate()).build();
        	JWKSet j = JWKSet.load(targetStream);
        	return JWSAlgorithmFamilyJWSKeySelector.fromJWKSource(new ImmutableJWKSet(j));
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new IllegalArgumentException(ex);
        }
    }
}
