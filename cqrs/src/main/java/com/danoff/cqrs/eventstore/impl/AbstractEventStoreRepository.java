package com.danoff.cqrs.eventstore.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.danoff.cqrs.core.BaseAggregateRoot;
import com.danoff.cqrs.core.Event;
import com.danoff.cqrs.eventstore.EventStoreRepository;

public abstract class AbstractEventStoreRepository implements EventStoreRepository{

	@Override
	public void saveEvent(Event<?> event) {
		getEventStore().appendEvent(event);
	}

	@Override
	public <T extends BaseAggregateRoot<?>> T getRehydratedAggregate(Class<T> type, String aggregateId) {
		try {
			T root = type.getConstructor(String.class).newInstance(aggregateId);
			Collection<Event<?>> events = getEventStore().readEvents(aggregateId, 0L);
		    if (events == null || events.size() == 0) {
		      return root;
		    }
			return replayEventsToAggregateRoot(type, root, events);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			 return null;
		}
	  }
	  
	  protected abstract <T extends BaseAggregateRoot<?>> T replayEventsToAggregateRoot(Class<T> type, T root, Collection<Event<?>> events);
}
