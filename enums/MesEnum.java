package br.com.branetlogistica.core.enums;



public enum MesEnum {
	
	JANEIRO("Janeiro",1,31),
	FEVEREIRO("Fevereiro",2,28),
	MARCO("Março",3,31),
	ABRIL("Abril",4,30),
	MAIO("Maio",5,31),
	JUNHO("Junho",6,30),
	JULHO("Julho",7,31),
	AGOSTO("Agosto",8,31),
	SETEMBRO("Setembro",9,30),
	OUTUBRO("Outubro",10,31),
	NOVEMBRO("Novembro",11,30),
	DEZEMBRO("Dezembro",12,31);
	
	
	private String label;
	private Integer numero;
	private Integer dias;
	
	private MesEnum(String label, Integer numero, Integer dias) {
		this.label = label;
		this.numero = numero;
		this.dias = dias;
	}
	
	public static int diasCorridos(MesEnum inicio, DiaMesEnum finall) {
		int dias =0;
		for(int i = inicio.ordinal() ; i <= finall.ordinal();i++) {	
			dias+=MesEnum.values()[i].getDias();
		}
		return dias;
	}
	
	public String getLabel() {
		return label;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getDias() {
		return dias;
	}

	
	
	
	
}