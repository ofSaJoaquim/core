package br.com.branetlogistica.core.log.auditory.interfaces.imp;

import java.time.LocalDateTime;
import java.util.List;

import br.com.branetlogistica.core.log.auditory.enums.LogAuditoryEventType;
import br.com.branetlogistica.core.log.auditory.interfaces.ILogAuditoryEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DataEvent implements ILogAuditoryEvent {

	private Object id;
	private Object[] oldState;
	private Object[] newState;
	private List<String> atributtes;
	private LogAuditoryEventType type;
	private String entityName;
	private LocalDateTime dateTime;
	private String entityId;
	
}