package br.com.branetlogistica.core.feriado;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class FeriadoMapper {

	public FeriadoDTO toDTO(Feriado entity) {
		if(entity == null)
			return null;
		return FeriadoDTO.builder()
				.id(entity.getId())
				.dataFeriado(entity.getDataFeriado().format(DateTimeFormatter.ISO_DATE))
				.descricao(entity.getDescricao())
				.build();
		
	}
	
	
	public FeriadoResponse toResponse(Feriado entity) {
		if(entity == null)
			return null;
		return FeriadoResponse.builder()
				.feriado(this.toDTO(entity))
				.build();
		
	}
	
}
