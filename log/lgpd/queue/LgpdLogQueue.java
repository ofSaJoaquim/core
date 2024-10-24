package br.com.branetlogistica.core.log.lgpd.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.branetlogistica.core.log.lgpd.dto.LgpdLogDto;
import br.com.branetlogistica.core.queue.QueueSender;

@Service
public class LgpdLogQueue {

	private final String logLgpdExchange = "LOG";
	private final String logLgpdKey = "LOG.lgpd";
	
	@Autowired
	private QueueSender sender;		
	
	public void sendLgpLog(LgpdLogDto dto) {
		sender.sendToExchange(dto, logLgpdExchange, logLgpdKey);
	}
	
}
