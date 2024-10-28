package br.com.branetlogistica.core.queue.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.queue.dto.QueueDto;
import br.com.branetlogistica.core.queue.factory.QueueFactory;
import br.com.branetlogistica.core.tenant.model.Tenant;
import br.com.branetlogistica.core.tenant.service.TenantService;


@Component
public class RabbitConfig {
	
	private final static Map<String,RabbitClient> clients = new HashMap<String, RabbitClient>();	
		
	@Value("${app.queue.host}")
	private String queueHost;
	
	@Value("${app.queue.port}")
	private Integer queuePort;
	
	@Value("${app.queue.username}")
	private String queueUserName;
	
	@Value("${app.queue.password}")
	private String queuePassword;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private QueueFactory queueFactory;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public RabbitClient getClient(String tenantId) {
		if(!clients.containsKey(tenantId)) {
			createClient(tenantId);
			clients.put(tenantId, createClient(tenantId));
		}
		return clients.get(tenantId);
	}
	
	public RabbitClient createClient(String tenantId) {
		ConnectionFactory connection = createConnection(tenantId);
		RabbitTemplate template =  createTemplate(connection);
		RabbitAdmin admin = new RabbitAdmin(connection);
		RabbitClient client = new RabbitClient(tenantId, connection, template, admin);
		return client;
	}
	
	public ConnectionFactory getConnection(String tenantId) {
		return getClient(tenantId).getConnectionFactory();
	}
	
	public RabbitTemplate getTemplate() {
		return getTemplate(Context.getContextData().getRequest().getClientId());
	}
	
	public RabbitTemplate getTemplate(String tenantId) {
		return getClient(tenantId).getRabbitTemplate();
	}
	
	private RabbitTemplate createTemplate(ConnectionFactory connectionFactory) {		
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		return template;
	}
	
	private ConnectionFactory createConnection(String tenantId) {
		Tenant tenant = tenantService.findTenantByClientId(tenantId);
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(this.queueHost);
		connectionFactory.setPort(this.queuePort);
		connectionFactory.setUsername(this.queueUserName);
		connectionFactory.setPassword(this.queuePassword);
		connectionFactory.setVirtualHost(tenant.getClientId());

		return connectionFactory;
	}
	
	public RabbitAdmin getRabbitAdmin(String tenantId) {
		return getClient(tenantId).getRabbitAdmin();
	}
	
	public List<RabbitAdmin> getAllRabbitAdmin(){
		List<Tenant> lista = tenantService.findAll();
		return lista.stream().map(x -> getRabbitAdmin(x.getClientId())).toList();		
	}
	
	public List<ConnectionFactory> getAllConnections(){
		List<Tenant> lista = tenantService.findAll();
		return lista.stream().map(x -> getConnection(x.getClientId())).toList();		
	}
	
	public void createAllListeners(String queueName, MessageListener listener) {
		List<Tenant> lista = tenantService.findAll();
//		queueBean.init(this);
		for(Tenant prop : lista) {
			this.messageListenerContainer(prop.getClientId(), queueName, listener); 
		}
	}
	
	public SimpleMessageListenerContainer messageListenerContainer(String tenantId, String queueName, MessageListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(getConnection(tenantId));
        container.setQueueNames(queueName);
        container.setMessageListener(listener);
        container.setAutoStartup(true);
        container.initialize();
        container.setAutoDeclare(true);
        container.start();
     
        return container;
    }
		
	public void convertAndSendWithContext(Object object, String exchange, String key) throws Exception {
		RabbitClient client = getClient(Context.getContextData().getRequest().getClientId());
		QueueDto queueDto  = queueFactory.createQueueDto(object);	
		String json = objectMapper.writeValueAsString(queueDto);
		client.getRabbitTemplate().convertAndSend(exchange, key , json );
	}
	

	
	
	

}
