package br.com.branetlogistica.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import br.com.branetlogistica.core.interfaces.IEntity;

public class Util {

	public static boolean  isValidId(Long id) {
		if(id == null || id < 1l)
			return false;
		return true;
			
	}
	
	public static boolean isValidaString(String string) {
		if(string == null || string.isBlank() || string.isEmpty())
			return false;
		return true;
	}
	
	
	public static boolean isValidLong(String string) {
		if(isValidaString(string)) {
			if(string.matches("^[0-9]"))
				return true;
		}
			return false;
		
	}
	
	public static boolean isEmpty(List<?> lista) {
		if(lista == null || lista.isEmpty())
			return true;
		return false;
	}
	
	public static Map<String, String> jsonMapper(String json) {
		Map<String, String> map = new HashMap<String, String>();
		json = json.substring(1, json.length() - 1);
	
		int teste = json.indexOf("context");
		if(teste<11) {
		
			String contextString = null;
			int i = 10;
			int ii = json.indexOf("},")+1;
			contextString = json.substring(i, ii);
			map.put("context", contextString);
			
			
			String dtoString = null;		
			dtoString = json.substring(ii+7);
			map.put("dto", dtoString);
		}else {
			
			String contextString = null;
			int i = teste;
			contextString = json.substring(i+9);
			map.put("context", contextString);
			
			
			String dtoString = null;		
			dtoString = json.substring(6,i-2);
			map.put("dto", dtoString);
			
		}
		
		
		return map;

	}
	
	public static Long getLongValue(String value) {
		if(value == null)
			return null;
		if(!Util.isValidLong(value))
			return null;
		return Long.parseLong(value);
	}
	
	public static boolean isNull(IEntity entidade) {
		if (entidade != null && entidade.getId() != null && entidade.getId() > 0)
			return false;
		return true;
	}
	
	
	
	public static boolean stringsEquals(String a, String b) {
		if (a == null && b == null)
			return true;
		if (a == null || b == null)
			return false;
		return a.equals(b);
	}
	
	// REFERÊNCIA: https://www.baeldung.com/java-foreach-counter
	public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
	    AtomicInteger counter = new AtomicInteger(0);
	    return item -> consumer.accept(counter.getAndIncrement(), item);
	}
	
	public static String toString(byte[] data) {
		try {
			return new String(data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
