package br.com.branetlogistica.core.log.auditory.factory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.metamodel.mapping.AttributeMappingsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.log.auditory.enums.LogAuditoryEventType;
import br.com.branetlogistica.core.log.auditory.interfaces.imp.DataEvent;
import br.com.branetlogistica.core.log.auditory.service.AuditoryLogService;
import jakarta.annotation.PostConstruct;

@Component
public class DataEventFactory {

	public static AuditoryLogService service;	
	public static boolean logAuditoryEnableStatic;
	
	@Value("${log.auditory.enable}")
	private boolean logAuditoryEnable;

	@Autowired
	private AuditoryLogService autowiredComponent;

	@PostConstruct
	private void init() {
		service = this.autowiredComponent;
		logAuditoryEnableStatic = logAuditoryEnable;
	}

	public static void createAndSendEvent(PostInsertEvent event) {
		service.sendAuditoryLog(createDataEvent(event));
	}

	public static void createAndSendEvent(PostUpdateEvent event) {
		service.sendAuditoryLog(createDataEvent(event));
	}

	public static void createAndSendEvent(PostDeleteEvent event) {
		service.sendAuditoryLog(createDataEvent(event));
	}

	public static DataEvent createDataEvent(PostInsertEvent event) {
		return create(LogAuditoryEventType.INSERT, event.getId(), event.getEntity().getClass().getSimpleName(),
				event.getState(), null, event.getPersister().getAttributeMappings());
	}

	public static DataEvent createDataEvent(PostUpdateEvent event) {
		return create(LogAuditoryEventType.UPDATE, event.getId(), event.getEntity().getClass().getSimpleName(),
				event.getState(), event.getOldState(), event.getPersister().getAttributeMappings());
	}

	public static DataEvent createDataEvent(PostDeleteEvent event) {
		return create(LogAuditoryEventType.DELETE, event.getId(), event.getEntity().getClass().getSimpleName(), null,
				event.getDeletedState(), event.getPersister().getAttributeMappings());
	}

	private static DataEvent create(LogAuditoryEventType type, Object id, String className, Object[] state,
			Object[] oldState, AttributeMappingsList attributes) {
		List<String> attr = new ArrayList<String>();
		//String entityId = null;
		LocalDateTime eventTime = null;
				
		
		
		attributes.forEach(attribute -> attr.add(attribute.getAttributeName()));
		for (int i = 0; i < attr.size(); i++) {					
			 if(attr.get(i).equals("version")) {
				if (type.equals(LogAuditoryEventType.INSERT) )
					eventTime =  (LocalDateTime)state[i];
				else
					eventTime =   (LocalDateTime)oldState[i];
				break;
			 }
		}
		
		
		return DataEvent.builder()
				.oldState(oldState)
				.newState(state)
				.entityName(className)
				.id(id)
				.type(type)
				.dateTime(eventTime)
				.atributtes(attr)
				.build();
	}
}