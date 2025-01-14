package br.com.branetlogistica.core.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.branetlogistica.core.context.Context;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionLink {

		
	@JsonIgnore
	private String uri;
	
	private String method;
	
	public String getHref() {
		return Context.getContextData().getRequest().getUrl()+uri;
	}
	
	public ActionLink(String uri, String method) {
		this.uri = uri;
		this.method = method;
	}
	
	
	public ActionLink(ActionLink actionLink, String uri) {		
		this.method = actionLink.getMethod();
		this.uri = uri;
	}
}
