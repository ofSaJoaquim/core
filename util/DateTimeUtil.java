package br.com.branetlogistica.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import br.com.branetlogistica.core.exceptions.impl.ApiException;
import br.com.branetlogistica.msagenda.modulos.cronograma.enums.DiaSemanaEnum;


public class DateTimeUtil {

	public static String formatDate(LocalDateTime date) {
	    Date utilDate = Date.from(date.toInstant(ZoneOffset.UTC));
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
	    return dateFormat.format(utilDate);
	}
	
	public static LocalDateTime toLocalDateTime(String date) {
		if(date == null || date.isBlank() || date.isEmpty())
			return null;
		return LocalDateTime.parse(date,DateTimeFormatter.ISO_DATE_TIME);
	}
	
	public static String toISO8601(LocalDateTime date) {
		if(date == null )
			return null;
		return date.format(DateTimeFormatter.ISO_DATE_TIME);
	}
	
	
	public static String toISO8601(LocalDate date) {
		if(date == null )
			return null;
		return date.format(DateTimeFormatter.ISO_DATE);
	}
	
	public static LocalDateTime proximoDiaUtil(LocalDateTime dataHoraTeste, Integer numeroDias, List<DiaSemanaEnum> diasSemana)  {
		LocalTime horaTeste = dataHoraTeste.toLocalTime();
		LocalDate dataTeste = proximoDiaUtil(dataHoraTeste.toLocalDate(), numeroDias, diasSemana);
		return LocalDateTime.of(dataTeste, horaTeste);
	}
	
	public static LocalDate proximoDiaUtil(LocalDate dataTeste, Integer numeroDias, List<DiaSemanaEnum> diasSemana)  {
		if(dataTeste == null)
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "DataUtil: Data teste não pode ser nula");
		
		if(diasSemana == null || diasSemana.isEmpty())
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Dias da semana não pode ser nula");
		
		if(numeroDias == null || numeroDias < 0)
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Numero de dias não pode ser nulo");
			
		
		LocalDate dataProcessada = LocalDate.from(dataTeste);
		Integer diasProcessado = -1;
		
		
		for(int i = 0; i < 10000; i++) {
			if(isDiaUtil(dataProcessada, diasSemana))
				diasProcessado++;	
			if(diasProcessado>=numeroDias)
				return dataProcessada;
			dataProcessada = dataProcessada.plusDays(1l);
			
		}
		throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Date teste passo do limite de interação permitidas");
		
		
			
	}
	
	
	public static boolean isDiaUtil(LocalDate dataTeste, List<DiaSemanaEnum> diasSemana) {
			
		if(dataTeste == null)
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Data teste não pode ser nula");
		
		if(diasSemana == null || diasSemana.isEmpty())
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Dias da semana não pode ser nula");
		
//		if(DataUtils.isFeriado(DataUtils.toDate(dataTeste))) {
//			if(!diasSemana.stream().anyMatch(x -> x.equals(DiaSemanaEnum.FERIADO)))
//				return false;
//		}
//			
		return diasSemana.stream().anyMatch(
				x -> x.getValue().equals(
						dataTeste.getDayOfWeek().getValue()));	
		
	}
	
}