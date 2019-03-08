package com.danoff.cqrs.eventstore;


import com.danoff.cqrs.core.BaseAggregateRoot;
import com.danoff.cqrs.core.Event;

public interface EventStoreRepository {
	
	void saveEvent(Event<?> event);
	
	<T extends BaseAggregateRoot<?>> T getRehydratedAggregate(Class<T> type, String aggregateId);

	EventStore getEventStore();

}
