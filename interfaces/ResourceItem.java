package br.com.branetlogistica.core.interfaces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceItem {

	private String url;
	private String type;
	private String action;
	private String key;
	
	
	
	
	
}
