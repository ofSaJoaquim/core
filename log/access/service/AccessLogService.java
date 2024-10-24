package br.com.branetlogistica.core.log.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.log.access.dto.AccessLogDTO;
import br.com.branetlogistica.core.log.access.queue.AccessLogQueue;

@Service
public class AccessLogService {
	
	@Value("${log.access.enable}")
	private boolean logAccessEnable;
	
	@Autowired
	private AccessLogQueue queue;
	
		
	public void sentAccessLog(Integer status) {	
		if(!logAccessEnable)
			return;
		
		sendLogs(new AccessLogDTO(Context.getContextData(), status));		
	}
	
	private void sendLogs(final AccessLogDTO dto) {
		Thread t1 = new Thread() {
			public void run() {
				try {

					queue.sendAccessLog(dto);

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		};
		t1.start();

	}
	
	
}
