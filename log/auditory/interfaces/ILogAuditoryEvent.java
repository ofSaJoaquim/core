package br.com.branetlogistica.core.log.auditory.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import br.com.branetlogistica.core.log.auditory.enums.LogAuditoryEventType;



public interface ILogAuditoryEvent {

	public LogAuditoryEventType getType();
	public Object[] getOldState();
	public Object[] getNewState();
	public List<String> getAtributtes();
	public Object getId();
	public String getEntityName();
	public LocalDateTime getDateTime();
}