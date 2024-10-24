package br.com.branetlogistica.core.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.branetlogistica.core.interfaces.IQueueDto;
import br.com.branetlogistica.core.queue.factory.QueueFactory;

@Service
public class QueueSender {

	@Autowired
	protected RabbitTemplate amqpTemplate;

	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	protected QueueFactory factory;

	public void sendToExchange(Object object, String exchange, String key) {
		try {

			String e = objectMapper.writeValueAsString(object);
			this.amqpTemplate.convertAndSend(exchange, key, e);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
	
	public void sendToExchange(IQueueDto dto, String exchange, String key) {
		try {			
			String e = objectMapper.writeValueAsString(factory.createQueueDto(dto));
			this.amqpTemplate.convertAndSend(exchange, key, e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

}
