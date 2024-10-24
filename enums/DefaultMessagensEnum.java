package br.com.branetlogistica.core.enums;

import lombok.Getter;

@Getter
public enum DefaultMessagensEnum {

	
	SUCCESS_CREATED("Cadastrado com sucesso"),
	SUCCESS_LOAD("Carregado com sucesso"),
	SUCCESS_UPDATED("Alterado com sucesso"),
	SUCCESS_DELETED("Removido com sucesso"),
	SUCCESS_DISABLED("Inativado com sucesso");
	
	
	
	private DefaultMessagensEnum(String messagem) {
		this.messagem = messagem;
	}

	private String messagem;
	
}
