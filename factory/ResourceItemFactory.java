package br.com.branetlogistica.core.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.context.ContextData;
import br.com.branetlogistica.core.interfaces.ResourceItem;

@Component
public class ResourceItemFactory {

	public ResourceItem createSelectResource(String key, String resourcePath, String action , String id) {
		ContextData contextData = Context.getContextData();		
		return ResourceItem.builder()
				.key(key)
				.type("GET")
				.url(contextData.getRequest().getUrl()+resourcePath+id)
				.action(action)
				.build();
	}
	
	public ResourceItem createInsertResource(String key, String resourcePath, String action) {
		ContextData contextData = Context.getContextData();		
		return ResourceItem.builder()
				.key(key)
				.type("POST")
				.url(contextData.getRequest().getUrl()+resourcePath)
				.action(action)
				.build();
	}
	
	public ResourceItem createUpdateResource(String key, String resourcePath,String action , String id) {
		ContextData contextData = Context.getContextData();		
		return ResourceItem.builder()
				.key(key)
				.type("PUT")
				.url(contextData.getRequest().getUrl()+resourcePath+id)
				.action(action)
				.build();
	}
	
	public ResourceItem createDeleteResource(String key, String resourcePath, String action , String id) {
		ContextData contextData = Context.getContextData();		
		return ResourceItem.builder()
				.key(key)
				.type("DELETE")
				.url(contextData.getRequest().getUrl()+resourcePath+id)
				.action(action)
				.build();
	}
	
	public ResourceItem createPageResource(String key, String resourcePath, String action , String id) {
		ContextData contextData = Context.getContextData();		
		return ResourceItem.builder()
				.key(key)
				.type("GET")
				.url(contextData.getRequest().getUrl()+resourcePath+id)
				.action(action)
				.build();
	}
	
	
	public List<ResourceItem> createDefaultResourcePage(String key, String resourcePath, String id){
		List<ResourceItem> itens = new ArrayList<ResourceItem>();
		itens.add(createSelectResource(key, resourcePath,"FIND", "/"+id));
		itens.add(createInsertResource(key+"_insert", resourcePath, "INSERT"));
		itens.add(createUpdateResource(key+"_update", resourcePath, "UPDATE","/"+id));
		itens.add(createDeleteResource(key+"_delete", resourcePath, "DISABLE" , "/"+id));
		
		return itens;
	}
	
	public List<ResourceItem> createDefaultFind(String key, String resourcePath, String id){
		List<ResourceItem> itens = new ArrayList<ResourceItem>();
		itens.add(createPageResource(key, resourcePath,"PAGE","?id="+id));		
		itens.add(createUpdateResource(key+"_update", resourcePath, "UPDATE","/"+id));
		itens.add(createDeleteResource(key+"_delete", resourcePath, "DISABLE" , "/"+id));
		
		return itens;
	}
	
	public List<ResourceItem> createDefaultInsertUpdatePage(String key, String resourcePath, String id ){
		List<ResourceItem> itens = new ArrayList<ResourceItem>();
		itens.add(createPageResource(key, resourcePath, "PAGE","?id="+id));
		itens.add(createSelectResource(key, resourcePath, "FIND","/"+id));		
		itens.add(createUpdateResource(key+"_update", resourcePath, "UPDATE","/"+id));
		itens.add(createDeleteResource(key+"_delete", resourcePath, "DISABLE" , "/"+id));
		return itens;
	}
	
	
	
}
