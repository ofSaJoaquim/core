package br.com.branetlogistica.core.enums;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiaSemanaEnum {
	DOMINGO(7,"domingo"),
	SEGUNDA(1,"segunda"),
	TERCA(2,"terca"),
	QUARTA(3,"quarta"),
	QUINTA(4,"quinta"),
	SEXTA(5,"sexta"),
	SABADO(6,"sabado"),
	FERIADO(8,"feriado");
	
	private Integer value;
	private String label;
	
	public static boolean possueDiaSemana(List<DiaSemanaEnum> lista) {
		if(lista == null || lista.isEmpty())
			return false;
		
		for(DiaSemanaEnum item:lista) {
			if(item.equals(DiaSemanaEnum.DOMINGO))
				return true;
			
			if(item.equals(DiaSemanaEnum.SEGUNDA))
				return true;
			
			if(item.equals(DiaSemanaEnum.TERCA))
				return true;
			
			if(item.equals(DiaSemanaEnum.QUARTA))
				return true;
			
			if(item.equals(DiaSemanaEnum.QUINTA))
				return true;
			
			if(item.equals(DiaSemanaEnum.SEXTA))
				return true;
			
			if(item.equals(DiaSemanaEnum.SABADO))
				return true;
			
		}
		
		return false;
	}
}
