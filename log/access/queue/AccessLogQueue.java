package br.com.branetlogistica.core.log.access.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.branetlogistica.core.log.access.dto.AccessLogDTO;
import br.com.branetlogistica.core.queue.QueueSender;



@Service
public class AccessLogQueue {	
	private final String logAccessKey = "LOG.access";
	private final String logAccessExchange = "LOG";

	@Autowired
	private QueueSender sender;

	
	public void sendAccessLog(AccessLogDTO dto) {
		sender.sendToExchange(dto, logAccessExchange, logAccessKey);
	}
	
}
