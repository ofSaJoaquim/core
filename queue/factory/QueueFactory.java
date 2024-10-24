package br.com.branetlogistica.core.queue.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.context.ContextUtil;
import br.com.branetlogistica.core.interfaces.IQueueDto;
import br.com.branetlogistica.core.interfaces.impl.QueueContextDto;
import br.com.branetlogistica.core.queue.dto.QueueDto;
import br.com.branetlogistica.core.util.Util;

@Service
public class QueueFactory {

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected ContextUtil contextUtil;

	public QueueContextDto createQueueContext() {
		return new QueueContextDto(Context.getContextData());
	}

	public QueueDto createQueueDto(IQueueDto dto) {
		return QueueDto.builder().context(createQueueContext()).dto(dto).build();

	}
	
	

	public <T extends IQueueDto> T createQueueDto(String json, String queueName , String className, String methodName, Class<T> clazz) {
		
		try {
			QueueContextDto queueDto;
			Map<String,String> map = Util.jsonMapper(json);
			String strContext = map.get("context").toString();
			queueDto = objectMapper.readValue(strContext, QueueContextDto.class);
			contextUtil.createContext(queueDto, queueName, clazz, methodName);
			return objectMapper.readValue(map.get("dto"), clazz);
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		
		return null;
	}

}
