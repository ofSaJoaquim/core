package br.com.branetlogistica.core.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.log.access.service.AccessLogService;

@Component
public class PosInterceptor {
	
	@Autowired
	public AccessLogService accessLogService;	
	
	public void posAction(Integer status) {
		try {
			if(Context.getContextData()!=null)
				accessLogService.sentAccessLog(status);			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			Context.clear();
		}
	}
	

}
