package com.danoff.cqrs.eventstore.impl.memory;

import java.util.Collection;

import com.danoff.cqrs.core.BaseAggregateRoot;
import com.danoff.cqrs.core.Event;
import com.danoff.cqrs.eventstore.EventStore;
import com.danoff.cqrs.eventstore.impl.AbstractEventStoreRepository;

public class SimpleEventStoreRepository extends AbstractEventStoreRepository{

	private EventStore eventStore;
	
	public SimpleEventStoreRepository(EventStore eventStore) {
		this.eventStore = eventStore;
	}
	
	@Override
	public EventStore getEventStore() {
		return eventStore;
	}

	@Override
	protected <T extends BaseAggregateRoot<?>> T replayEventsToAggregateRoot(Class<T> type, T root, Collection<Event<?>> events) {
		for (Event<?> event : events) {
			try {
				root = type.cast(root.apply(event));
			} catch (Exception e) {
				throw new IllegalStateException("AggregateRoot replay fail.", e);
			}
		}

		return root;
	}

}
