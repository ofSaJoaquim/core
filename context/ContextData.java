package br.com.branetlogistica.core.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class ContextData {
	
	private final ContextIdentity identity;
	private final ContextRequest request;
	
	

}
