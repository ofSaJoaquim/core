package br.com.branetlogistica.core.security.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientToken {

	private ResponseToken responseToken;
	private Long expiresAt;
	
}
