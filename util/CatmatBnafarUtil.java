package br.com.branetlogistica.core.util;

public class CatmatBnafarUtil {
	
	public static boolean isValido(String catmat) {
        if(!Util.isValidaString(catmat)) {
        	return false;
        }
        if(catmat.length() < 14) {
            return false;
        }
		if (!catmat.startsWith("BR")) {
			return false;
		}
        return true;
	}
}
