package br.com.branetlogistica.core.log.lgpd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.log.lgpd.dto.LgpdLogDto;
import br.com.branetlogistica.core.log.lgpd.queue.LgpdLogQueue;

@Component
public class LgpdLogService {

	@Value("${log.access.enable}")
	private boolean logLgpdEnable;
	
	@Autowired
	private LgpdLogQueue queue;

	public void sendLgpdLog(List<Long> peopleIds, List<String> peopleFields) {
		if(!logLgpdEnable)
			return;
		sendLogs(new LgpdLogDto(Context.getContextData(), peopleIds, peopleFields));
	}

	private void sendLogs(final LgpdLogDto dto) {
		Thread t1 = new Thread() {
			public void run() {
				try {

					queue.sendLgpLog(dto);

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		};
		t1.start();

	}

}
