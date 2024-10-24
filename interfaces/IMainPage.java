package br.com.branetlogistica.core.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IMainPage<T> {

	public Page<T> getPage();
	public void setPage(Page<T> page);
	
	public void setResources(List<String> resources);
	public List<String> getResources();
	
	public void setListResources(List<String> listResources);
	public List<String> getListResources();

}
