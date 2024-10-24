package br.com.branetlogistica.core.interfaces.impl;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.branetlogistica.core.interfaces.IMainPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MainPage<T> implements IMainPage<T> {

	private Page<T> page;
	private List<String>resources;
	private List<String>listResources;
	public MainPage(Page<T> page) {
		super();
		this.page = page;
		
	}
	
	

}
