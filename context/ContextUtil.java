package br.com.branetlogistica.core.context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import br.com.branetlogistica.core.exceptions.impl.ApiException;
import br.com.branetlogistica.core.interfaces.impl.QueueContextDto;
import br.com.branetlogistica.core.security.client.SecurityClient;
import br.com.branetlogistica.core.util.RequestUtil;
import br.com.branetlogistica.core.util.Util;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ContextUtil {
	
	@Autowired
	private SecurityClient securityClient;
	
	@Value("${spring.application.name}")
	private String serviceName;
	
	@Value("${app.version}")
	private String appVersion;
	
	public ContextIdentity  createContextIdentity(Authentication authentication, HttpServletRequest request ) {		
		final Jwt jwt = (Jwt)authentication.getPrincipal();		
		if(authentication.isAuthenticated())
			return ContextIdentity.builder()				
				.userId(Util.getLongValue(jwt.getClaimAsString("user_id")))	
				.userName(jwt.getClaimAsString("preferred_username"))
				.coastCenterId(Util.getLongValue(request.getHeader("X-COAST_CENTER-ID")))
				.appClient(jwt.getClaimAsString("azp"))
				.build();
		return null;
	}
	
	public ContextRequest  createContextRequest(HttpServletRequest request, HandlerMethod handlerMethod ) {
		StringBuffer url = new StringBuffer();
		url.append(request.getRequestURL());
		url.setLength(request.getRequestURL().length() - request.getServletPath().length());
		return ContextRequest.builder()
				.dateEvent(LocalDateTime.now())
				.ipAddress(request.getRemoteAddr())
				.url(url.toString())
				.uri(request.getServletPath())
				.className(handlerMethod.getBeanType().getSimpleName())
				.methodName(handlerMethod.getMethod().getName())
				.transactionUUID(request.getHeader("X-TRANSACTION-ID"))
				.serviceName(serviceName)
				.appVersion(appVersion)
				.clientId(request.getHeader("X-CLIENT-ID"))
				.build();				
	}

	public void createContext(HttpServletRequest request, Object object) {
		final HandlerMethod handlerMethod = (HandlerMethod) object;	
	//	final SecurityAnnotationValidation s = handlerMethod.getMethod().getAnnotation(SecurityAnnotationValidation.class);
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Context.setContextData(
					ContextData.builder()
					.identity(createContextIdentity(authentication, request))
					.request(createContextRequest(request, handlerMethod))
					.build());				
		
		final Jwt jwt = (Jwt)authentication.getPrincipal();
		if(request.getHeader("X-CLIENT-ID") == null)
			throw new ApiException(HttpStatus.BAD_REQUEST, "O header 'X-CLIENT-ID' não foi informado");
		
		if(!request.getHeader("X-CLIENT-ID").equals(jwt.getClaim("CLIENT-ID")))
			throw new ApiException(HttpStatus.BAD_REQUEST, "O header 'X-CLIENT-ID' não corresponde ao 'CLIENT-ID' do token");
		
	
	}	
	
	
	
	
	public ContextIdentity  createContextIdentity(QueueContextDto contextDto, String appClient) {		
			return ContextIdentity.builder()				
				.userId(contextDto.getUserId())	
				.userName(contextDto.getUserName())
				.coastCenterId(contextDto.getCoastCenterId())
				.appClient(appClient)
				.build();		
	}
	
	public ContextRequest  createContextRequest(QueueContextDto contextDto, String queueName ,Class<?> clazz , String methodName ) {		
		return ContextRequest.builder()
				.dateEvent(LocalDateTime.now())
				.ipAddress("RABBIT_MQ")
				.url("RABBIT_MQ")
				.uri(queueName)
				.className(clazz.getSimpleName())
				.methodName(methodName)
				.transactionUUID(contextDto.getTransactionUUID())
				.serviceName(serviceName)
				.appVersion(appVersion)
				.clientId(contextDto.getClientId())
				.build();				
	}
	
	public void createContext(QueueContextDto contextDto, String queueName ,Class<?> clazz , String methodName ) {		
		Context.setContextData(
				ContextData.builder()
				.identity(createContextIdentity(contextDto,"RABBIT_MQ"))
				.request(createContextRequest(contextDto, queueName, clazz, methodName))
				.build());		
		
	}
	
	public static Map<String, Object> createValidHeader(Integer apiVersion) {
		ContextData contextData = Context.getContextData();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", RequestUtil.getCurrentRequest().getHeader("Authorization"));
		headers.put("X-API-VERSION", apiVersion);
		headers.put("X-TRANSACTION-ID", contextData.getRequest().getTransactionUUID());
		
		if(contextData.getIdentity()!=null)
			headers.put("X-COAST-CENTER-ID", contextData.getIdentity().getCoastCenterId());
		

		return headers;

	}
	
	public Map<String, Object> createRedirectHeader(Integer apiVersion) {
		ContextData contextData = Context.getContextData();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", securityClient.getResponseToken() );
		headers.put("X-API-VERSION", apiVersion);
		if(contextData!=null) {
			if(contextData.getRequest().getTransactionUUID()!=null)
				headers.put("X-TRANSACTION-ID", contextData.getRequest().getTransactionUUID());
			
			if(contextData.getIdentity()!=null)
				headers.put("X-COAST-CENTER-ID", contextData.getIdentity().getCoastCenterId());
		}
		return headers;

	}
	
	public HttpHeaders createRedirectHeaderRestTemplate(Integer apiVersion) {
		ContextData contextData = Context.getContextData();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", securityClient.getResponseToken());
		headers.add("X-API-VERSION", apiVersion.toString());
		headers.add("X-TRANSACTION-ID", contextData.getRequest().getTransactionUUID());
		
		if(contextData.getIdentity()!=null)
			headers.add("X-COAST-CENTER-ID", contextData.getIdentity().getCoastCenterId().toString());
		return headers;

	}

}
