package br.com.branetlogistica.core.util;

import java.text.Normalizer;

public class CnsUtil {
	
		
	public static String removerCaracteresEspeciais(String texto) {
		if (texto != null) {
			texto = texto.toUpperCase();
			texto = texto.replaceAll(" +", " ");
			texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
			texto = texto.replaceAll("[^A-Z^0-9%/^\\-^+^(^)^,^Âº^ ^]", "");
			texto = texto.trim();
		}
		return texto;
	}
	
	public static Integer converterNumero(String texto) {
		Integer numero = null;
		if (texto != null) {
			texto = texto.replaceAll("[^0-9]", "");
			if (!texto.isEmpty()) {
				numero = Integer.parseInt(texto);
			}
		}
		return numero;
	}
	
	public static boolean validaCNS(String cns) {
		
		if (cns != null && cns.trim().length() == 15) {
			
			if (cns.trim().substring(0, 1).equals("1") || cns.trim().substring(0, 1).equals("2"))
				return validaCns(cns.trim());
			else if (cns.trim().substring(0, 1).equals("7") || cns.trim().substring(0, 1).equals("8") || cns.trim().substring(0, 1).equals("9"))
				return validaCnsProv(cns.trim());
		
		}
		
		return false;
	}
	
	
	// Iniciam com 1 e 2
    private static boolean validaCns(String cns){
        if (cns.trim().length() != 15){
            return(false);
        }

        float soma;
        float resto, dv;
        String pis = new String("");
        String resultado = new String("");
        pis = cns.substring(0,11);

        soma = ((Integer.valueOf(pis.substring(0,1)).intValue()) * 15) +
               ((Integer.valueOf(pis.substring(1,2)).intValue()) * 14) +
               ((Integer.valueOf(pis.substring(2,3)).intValue()) * 13) +
               ((Integer.valueOf(pis.substring(3,4)).intValue()) * 12) +
               ((Integer.valueOf(pis.substring(4,5)).intValue()) * 11) +
               ((Integer.valueOf(pis.substring(5,6)).intValue()) * 10) +
               ((Integer.valueOf(pis.substring(6,7)).intValue()) * 9) +
               ((Integer.valueOf(pis.substring(7,8)).intValue()) * 8) +
               ((Integer.valueOf(pis.substring(8,9)).intValue()) * 7) +
               ((Integer.valueOf(pis.substring(9,10)).intValue()) * 6) +
               ((Integer.valueOf(pis.substring(10,11)).intValue()) * 5);

        resto = soma % 11;
        dv = 11 - resto;

        if (dv == 11){
            dv = 0;
        }

        if (dv == 10){
            soma = ((Integer.valueOf(pis.substring(0,1)).intValue()) * 15) +
                   ((Integer.valueOf(pis.substring(1,2)).intValue()) * 14) +
                   ((Integer.valueOf(pis.substring(2,3)).intValue()) * 13) +
                   ((Integer.valueOf(pis.substring(3,4)).intValue()) * 12) +
                   ((Integer.valueOf(pis.substring(4,5)).intValue()) * 11) +
                   ((Integer.valueOf(pis.substring(5,6)).intValue()) * 10) +
                   ((Integer.valueOf(pis.substring(6,7)).intValue()) * 9) +
                   ((Integer.valueOf(pis.substring(7,8)).intValue()) * 8) +
                   ((Integer.valueOf(pis.substring(8,9)).intValue()) * 7) +
                   ((Integer.valueOf(pis.substring(9,10)).intValue()) * 6) +
                   ((Integer.valueOf(pis.substring(10,11)).intValue()) * 5) + 2;

            resto = soma % 11;
            dv = 11 - resto;
            resultado = pis + "001" + String.valueOf((int)dv);
        }else {
            resultado = pis + "000" + String.valueOf((int)dv);
        }

        if (!cns.equals(resultado)){
            return(false);
        }else {
        return(true);}
    }
    

    // Iniciam com 7, 8 e 9
    private static boolean validaCnsProv(String cns){
        if (cns.trim().length() != 15){
            return(false);
        }

        float resto,soma;

        try {
			soma = ((Integer.valueOf(cns.substring(0, 1)).intValue()) * 15) +
				((Integer.valueOf(cns.substring(1, 2)).intValue()) * 14) +
				((Integer.valueOf(cns.substring(2, 3)).intValue()) * 13) +
				((Integer.valueOf(cns.substring(3, 4)).intValue()) * 12) +
				((Integer.valueOf(cns.substring(4, 5)).intValue()) * 11) +
				((Integer.valueOf(cns.substring(5, 6)).intValue()) * 10) +
				((Integer.valueOf(cns.substring(6, 7)).intValue()) * 9) +
				((Integer.valueOf(cns.substring(7, 8)).intValue()) * 8) +
				((Integer.valueOf(cns.substring(8, 9)).intValue()) * 7) +
				((Integer.valueOf(cns.substring(9, 10)).intValue()) * 6) +
				((Integer.valueOf(cns.substring(10, 11)).intValue()) * 5) +
				((Integer.valueOf(cns.substring(11, 12)).intValue()) * 4) +
				((Integer.valueOf(cns.substring(12, 13)).intValue()) * 3) +
				((Integer.valueOf(cns.substring(13, 14)).intValue()) * 2) +
				((Integer.valueOf(cns.substring(14, 15)).intValue()) * 1);
			resto = soma % 11;
			if (resto != 0){
				return(false);
			} else {
				return(true);}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
	
	
}
