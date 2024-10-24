package br.com.branetlogistica.core.log.dto;

import java.time.format.DateTimeFormatter;

import br.com.branetlogistica.core.context.ContextRequest;
import lombok.Getter;

@Getter
public class RequestLog {

	private final String serviceName;
	private final String methodName;
	private final String className;	
	private final String transactionUUID;
	private final String ipAddress;
	private final String url;	
	private final String dateTimeEvent;
	private final String clientId;
	private final String appVersion;
	
	public RequestLog(ContextRequest request) {
		super();
		this.serviceName = request.getServiceName();
		this.appVersion = request.getAppVersion();
		this.methodName = request.getMethodName();
		this.className = request.getClassName();
		this.transactionUUID = request.getTransactionUUID();
		this.ipAddress = request.getIpAddress();
		this.url = request.getUrl()+request.getUri();
		this.dateTimeEvent = request.getDateEvent().format(DateTimeFormatter.ISO_DATE_TIME);
		this.clientId = request.getClientId();
		
	}	
	
	
	
}
