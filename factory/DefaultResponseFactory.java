package br.com.branetlogistica.core.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.enums.DefaultMessagensEnum;
import br.com.branetlogistica.core.interfaces.DefaultResponse;
import br.com.branetlogistica.core.interfaces.IResponseDto;

@Component
public class DefaultResponseFactory {

	@Autowired
	private ResourceItemFactory resourceItemFactory;
	
	public ResponseEntity<?> createDefaulInsertResponse(IResponseDto response, String uri, String key){
		return new DefaultResponse<IResponseDto>(HttpStatus.CREATED, 
				DefaultMessagensEnum.SUCCESS_CREATED.getMessagem(), 
				resourceItemFactory.createDefaultInsertUpdatePage(key, uri, response.getId().toString()), 
				response)
				.createResponse();	
		
	}
	
	public ResponseEntity<?> createDefaultUpdateResponse(IResponseDto response, String uri, String key){
		return new DefaultResponse<IResponseDto>
		(HttpStatus.OK, 
		DefaultMessagensEnum.SUCCESS_UPDATED.getMessagem(), 
		resourceItemFactory.createDefaultInsertUpdatePage(key, uri, response.getId().toString()), 
		response)
		.createResponse();	
	}
	
	public ResponseEntity<?> createDefaultFindResponse(IResponseDto response, String uri, String key){
		return new DefaultResponse<IResponseDto>
		(HttpStatus.OK, 
		DefaultMessagensEnum.SUCCESS_LOAD.getMessagem(), 
		resourceItemFactory.createDefaultFind(key, uri, response.getId().toString()), 
		response)
		.createResponse();	
	}
	
	public ResponseEntity<?> createDefaultDisableResponse( String uri, String key){
		return new DefaultResponse<IResponseDto>
		(HttpStatus.OK, 
		DefaultMessagensEnum.SUCCESS_DISABLED.getMessagem(),null ,null)
		.createResponse();	
	}
	
	public ResponseEntity<?> createDefaultDeleteResponse( String uri, String key){
		return new DefaultResponse<IResponseDto>
		(HttpStatus.OK, 
		DefaultMessagensEnum.SUCCESS_DELETED.getMessagem(),null ,null)
		.createResponse();	
	}
	
	public ResponseEntity<?> createDefaultPageResponse(Page<?> response, String uri, String key){
		return new DefaultResponse<Page<?>>
		(HttpStatus.OK, 
		DefaultMessagensEnum.SUCCESS_LOAD.getMessagem(), 
		resourceItemFactory.createDefaultFind(key, uri, "id"), 
		response)
		.createResponse();	
	}
	
}
