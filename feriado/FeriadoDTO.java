package br.com.branetlogistica.core.feriado;

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
public class FeriadoDTO {

	private Long id;
	private String dataFeriado;
	private String descricao;
	
}
