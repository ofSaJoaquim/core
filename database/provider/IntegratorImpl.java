package br.com.branetlogistica.core.database.provider;



import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import br.com.branetlogistica.core.log.auditory.listener.EventListenerImpl;

public class IntegratorImpl implements Integrator {
	private static final IntegratorImpl INSTANCE = new IntegratorImpl();

	public static IntegratorImpl getInstance() {
		return INSTANCE;
	}

	private EventListenerImpl eventListenerImpl;

	private IntegratorImpl() {
		super();
		this.eventListenerImpl = EventListenerImpl.getInstance();
	}

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
		eventListenerRegistry.appendListeners(EventType.POST_UPDATE, eventListenerImpl);
		eventListenerRegistry.appendListeners(EventType.POST_INSERT, eventListenerImpl);
		eventListenerRegistry.appendListeners(EventType.POST_DELETE, eventListenerImpl);

	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

	}
}