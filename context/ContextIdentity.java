package br.com.branetlogistica.core.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ContextIdentity {

	private final Long userId;
	private final String userName;
	private final String appClient;	
	private final Long coastCenterId;
	
	
}
