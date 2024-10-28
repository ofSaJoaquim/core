package br.com.branetlogistica.core.interfaces.impl;

import br.com.branetlogistica.core.context.ContextData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueContextDto {
	
	private Long userId;
	private String userName;
	private Long coastCenterId;
	private String transactionUUID;
	private String clientId;
	
	
	public QueueContextDto(ContextData contextData) {
		super();
		if(contextData.getIdentity()!=null) {
			this.userId = contextData.getIdentity().getUserId();
			this.userName = contextData.getIdentity().getUserName();			
			this.coastCenterId = contextData.getIdentity().getCoastCenterId();
			
			
		}else {
			this.userId = null;
			this.userName = null;			
			this.coastCenterId = null;
		}
		this.transactionUUID = contextData.getRequest().getTransactionUUID();
		this.clientId = contextData.getRequest().getClientId();
		
		
	}


	
	
}
