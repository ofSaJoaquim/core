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

import br.com.branetlogistica.core.enums.DiaMesEnum;
import br.com.branetlogistica.core.enums.DiaSemanaEnum;
import br.com.branetlogistica.core.exceptions.impl.ApiException;


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
	
	public static LocalDateTime proximoDiaUtil(LocalDateTime dataHoraTeste, Integer numeroDias, List<DiaSemanaEnum> diasSemana, List<DiaMesEnum> diasMes)  {
		LocalTime horaTeste = dataHoraTeste.toLocalTime();
		LocalDate dataTeste = proximoDiaUtil(dataHoraTeste.toLocalDate(), numeroDias, diasSemana, diasMes);
		return LocalDateTime.of(dataTeste, horaTeste);
	}
	
	public static LocalDateTime proximoDiaUtil(LocalDateTime dataHoraTeste, Integer numeroDias, List<DiaSemanaEnum> diasSemana)  {
		LocalTime horaTeste = dataHoraTeste.toLocalTime();
		LocalDate dataTeste = proximoDiaUtil(dataHoraTeste.toLocalDate(), numeroDias, diasSemana, null);
		return LocalDateTime.of(dataTeste, horaTeste);
	}
	
	public static LocalDate proximoDiaUtil(LocalDate dataTeste, Integer numeroDias, 
			List<DiaSemanaEnum> diasSemana, List<DiaMesEnum> diasMes)  {
		if(dataTeste == null)
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "DataUtil: Data teste não pode ser nula");
		
		if((diasSemana == null || diasSemana.isEmpty()) && (diasMes == null || diasMes.isEmpty()))
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Ambos dias da semana e dias mês não pode ser nulos");
		
		if(numeroDias == null || numeroDias < 0)
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Numero de dias não pode ser nulo");
			
		
		LocalDate dataProcessada = LocalDate.from(dataTeste);
		Integer diasProcessado = -1;
		
		
		for(int i = 0; i < 10000; i++) {
			if(isDiaUtil(dataProcessada, diasSemana, diasMes))
				diasProcessado++;	
			if(diasProcessado>=numeroDias)
				return dataProcessada;
			dataProcessada = dataProcessada.plusDays(1l);
			
		}
		throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Date teste passo do limite de interação permitidas");
		
		
			
	}
	
	private static boolean isDiaSemanaUtil(LocalDate dataTeste, List<DiaSemanaEnum> diasSemana) {
		if(diasSemana == null || diasSemana.isEmpty())
			return true;
				
		return diasSemana.stream().anyMatch(
				x -> x.getValue().equals(
						dataTeste.getDayOfWeek().getValue()));	
	}
	
	public static boolean isDiaUtil(LocalDate dataTeste, List<DiaSemanaEnum> diasSemana) {
		return isDiaUtil(dataTeste, diasSemana, null);
	}
	
	public static boolean isDiaUtil(LocalDate dataTeste, List<DiaSemanaEnum> diasSemana, List<DiaMesEnum> diasMes) {
			
		if(dataTeste == null)
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Data teste não pode ser nula");
		
		if((diasSemana == null || diasSemana.isEmpty()) && (diasMes == null || diasMes.isEmpty()))
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"DataUtil: Ambos dias da semana e dias mês não pode ser nulos");
		
		if(isDiaSemanaUtil(dataTeste, diasSemana) && isDiaMesUtil(dataTeste, diasMes))
			return true;
		return false;
		
	}
	
	
	private static boolean isDiaMesUtil(LocalDate dataTeste, List<DiaMesEnum> diasMes) {		
		if(diasMes == null || diasMes.isEmpty())
			return true;
		
		return diasMes.stream().anyMatch(
				x -> x.getValue().equals(
						dataTeste.getDayOfMonth()));
		
	}
	
}