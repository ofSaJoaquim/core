package br.com.branetlogistica.core.log.auditory.listener;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

import br.com.branetlogistica.core.log.auditory.factory.DataEventFactory;

public class EventListenerImpl implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

	private static final EventListenerImpl INSTANCE = new EventListenerImpl();

	

	public static EventListenerImpl getInstance() {
		return INSTANCE;
	}

	private EventListenerImpl() {
		super();
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		if (DataEventFactory.logAuditoryEnableStatic) {
			final PostInsertEvent logeEvent = event;
			Thread t1 = new Thread() {
				public void run() {
					try {
						DataEventFactory.createAndSendEvent(logeEvent);
					} catch (Exception e) {
						e.printStackTrace();

					}

				}
			};
			t1.start();
			return;

		}

	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		if (DataEventFactory.logAuditoryEnableStatic) {
			final PostUpdateEvent logeEvent = event;
			Thread t1 = new Thread() {
				public void run() {
					try {
						DataEventFactory.createAndSendEvent(logeEvent);
					} catch (Exception e) {
						e.printStackTrace();

					}

				}
			};
			t1.start();
			return;
		}

	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		if (DataEventFactory.logAuditoryEnableStatic) {
			final PostDeleteEvent logeEvent = event;
			Thread t1 = new Thread() {
				public void run() {
					try {
						DataEventFactory.createAndSendEvent(logeEvent);
					} catch (Exception e) {
						e.printStackTrace();

					}

				}
			};
			t1.start();
			return;
		}

	}

	@Override
	public boolean requiresPostCommitHandling(EntityPersister persister) {
		// TODO Auto-generated method stub
		return false;
	}

}