package br.com.branetlogistica.core.queue.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import lombok.Getter;

@Getter
public class RabbitClient {

	private final String clientId;
	private final ConnectionFactory connectionFactory;
	private final RabbitTemplate rabbitTemplate;
	private final RabbitAdmin rabbitAdmin;
	private final Map<String, AbstractExchange>exchanges = new HashMap<String, AbstractExchange>();
	
	public RabbitClient(String clientId, ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin) {
		super();
		this.clientId = clientId;
		this.connectionFactory = connectionFactory;
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitAdmin = rabbitAdmin;
		
	}
	
	
	
}
