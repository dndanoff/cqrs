package com.danoff.cqrs.eventstore;

import java.util.Collection;

import com.danoff.cqrs.core.Event;

public interface EventStore {
	void appendEvent(Event<?> event);
	Collection<Event<?>> readEvents(String aggregateId, long fromVersion);
}
