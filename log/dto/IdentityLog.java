package br.com.branetlogistica.core.log.dto;

import br.com.branetlogistica.core.context.ContextIdentity;
import lombok.Getter;

@Getter
public class IdentityLog {

	private final Long userId;	
	private final String userName;
	private final String appClient;	
	private final Long coastCenterId;
	public IdentityLog(ContextIdentity contextIdentity) {
		super();
		this.userId = contextIdentity.getUserId();	
		this.userName = contextIdentity.getUserName();
		this.appClient = contextIdentity.getAppClient();
		this.coastCenterId = contextIdentity.getCoastCenterId();
	}
	
	
	
}
