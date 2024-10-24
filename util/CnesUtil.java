package br.com.branetlogistica.core.util;

import java.util.HashSet;
import java.util.Set;

public class CnesUtil {

	public static boolean isValido(String cnes) {
		if(!Util.isValidaString(cnes)) {
			return false;
		}
        if(cnes.length() != 7) {
            return false;
        }
        if(!Util.isValidLong(cnes)) {
        	return false;
        }
		if (!cnes.matches("^[0-9]*$")) {
			return false;
		}
		if (containsOnlyOneDigit(cnes)) {
			return false;
		}
		
		return true;
	}
	
	private static boolean containsOnlyOneDigit(String str) {
	    if (str == null || str.isEmpty()) {
	        return false;
	    }
	    Set<Character> digitSet = new HashSet<>();
	    for (char ch : str.toCharArray()) {
	        digitSet.add(ch);
	    }
	    return digitSet.size() == 1;
	}
}
