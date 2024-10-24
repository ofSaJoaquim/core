package br.com.branetlogistica.core.log.auditory.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.log.auditory.dto.AuditoryLogDto;
import br.com.branetlogistica.core.queue.QueueSender;

@Component
public class AuditoryLogQueue {

	private final String logAuditoryExchange = "LOG";
	private final String logAuditoryKey ="LOG.auditory";
	
	@Autowired
	private QueueSender sender;


	public void sendAccessLog(AuditoryLogDto dto) {
		sender.sendToExchange(dto, logAuditoryExchange, logAuditoryKey);
	}

}
