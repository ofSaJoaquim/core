package br.com.branetlogistica.core.context;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ContextRequest {

	private final String methodName;
	private final String className;	
	private final String transactionUUID;
	private final String ipAddress;
	private final String uri;
	private final String url;
	private final LocalDateTime dateEvent;
	private final String clientId;
	private final String serviceName;
	private final String appVersion;
	
	
}
