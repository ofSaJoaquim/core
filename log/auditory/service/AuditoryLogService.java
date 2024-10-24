package br.com.branetlogistica.core.log.auditory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.context.ContextData;
import br.com.branetlogistica.core.log.LogUtil;
import br.com.branetlogistica.core.log.auditory.dto.AuditoryLogDto;
import br.com.branetlogistica.core.log.auditory.interfaces.ILogAuditoryEvent;
import br.com.branetlogistica.core.log.auditory.queue.AuditoryLogQueue;

@Component
public class AuditoryLogService {	
		
	@Autowired
	private AuditoryLogQueue sender ;
	

	public void sendAuditoryLog(ILogAuditoryEvent auditoryEvent) {
		try {
			sender.sendAccessLog(this.create(auditoryEvent));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private AuditoryLogDto create(ILogAuditoryEvent event) throws Exception {
		boolean hasOld = event.getOldState() != null && event.getOldState().length > 0 ? true : false;
		boolean hasNew = event.getNewState() != null && event.getNewState().length > 0 ? true : false;
		List<String> oldData = new ArrayList<String>();
		List<String> newData = new ArrayList<String>();
		List<String> attibutes = new ArrayList<String>();
		ContextData contextData = Context.getContextData();

		for (int i = 0; i < event.getAtributtes().size(); i++) {

			Object oldObject = null;
			Object newObject = null;

			String oldValue = null;
			String newValue = null;

			if (hasOld) {
				oldObject = event.getOldState()[i];
				oldValue = LogUtil.getValueField(oldObject);
			}

			if (hasNew) {
				newObject = event.getNewState()[i];
				newValue = LogUtil.getValueField(newObject);
			}

			if (LogUtil.isEqual(oldValue, newValue))
				continue;

			String att = LogUtil.getFieldName(oldObject, newObject, event.getAtributtes().get(i));

			if (att != null && (oldValue != null || newValue != null)) {
				attibutes.add(att);
				if (oldValue != null)
					oldData.add(oldValue);

				if (newValue != null)
					newData.add(newValue);

			}

		}
		return new AuditoryLogDto(contextData, event, oldData, newData, attibutes);
	}

}
