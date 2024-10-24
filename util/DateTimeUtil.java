package br.com.branetlogistica.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
	
}