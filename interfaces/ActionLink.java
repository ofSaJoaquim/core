package br.com.branetlogistica.core.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.branetlogistica.core.context.Context;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionLink {

	private String action;
	
	@JsonIgnore
	private String uri;
	
	private String method;
	
	public String getHref() {
		return Context.getContextData().getRequest().getUrl()+uri;
	}
	
	public ActionLink(String action, String uri, String method) {
		this.action = action;
		this.uri = uri;
		this.method = method;
	}
	
	
	public ActionLink(ActionLink actionLink, String uri) {
		this.action = actionLink.getAction();
		this.method = actionLink.getMethod();
		this.uri = uri;
	}
}
