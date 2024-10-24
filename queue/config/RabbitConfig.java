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

import br.com.branetlogistica.core.context.Context;
import br.com.branetlogistica.core.tenant.model.Tenant;
import br.com.branetlogistica.core.tenant.service.TenantService;


@Component
public class RabbitConfig {

	
	private final static Map<String,ConnectionFactory> connections = new HashMap<String, ConnectionFactory>();	
	private final static Map<String,RabbitTemplate> templates = new HashMap<String, RabbitTemplate>();	
	
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
	
	public ConnectionFactory getConnection(String tenantId) {
		if(!connections.containsKey(tenantId))		
			connections.put(tenantId, createConnection(tenantId));
		return connections.get(tenantId);
	}
	
	public RabbitTemplate getTemplate() {
		return getTemplate(Context.getContextData().getRequest().getClientId());
	}
	
	public RabbitTemplate getTemplate(String tenantId) {
		if(!templates.containsKey(tenantId))
			createTemplate(tenantId);
		return templates.get(tenantId);
	}
	
	private RabbitTemplate createTemplate(String tenantId) {
		RabbitTemplate template = new RabbitTemplate(getConnection(tenantId));
		templates.put(tenantId, template);
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
		return new RabbitAdmin(getConnection(tenantId));
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
	
	
	

}
