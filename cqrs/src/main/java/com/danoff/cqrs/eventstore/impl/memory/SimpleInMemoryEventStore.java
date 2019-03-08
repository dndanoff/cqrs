package com.danoff.cqrs.eventstore.impl.memory;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.danoff.cqrs.core.Event;
import com.danoff.cqrs.eventstore.EventStore;

public class SimpleInMemoryEventStore implements EventStore {
	
	private final Map<String, NavigableMap<Long, Event<?>>> events;
	
	public SimpleInMemoryEventStore() {
		this.events = new HashMap<>();
	}
	
	@Override
	public void appendEvent(Event<?> event) {
		String aggregateId = event.getAggregateId();
		
		if (!events.containsKey(aggregateId)) {
			events.put(aggregateId, new TreeMap<>());
		}
		
		events.get(aggregateId).put(event.getVersion(), event);
		
	}

	@Override
	public Collection<Event<?>> readEvents(String aggregateId, long fromVersion) {
		if(aggregateId == null || events.get(aggregateId) == null) {
			return Collections.emptyList();
		}
		
		return events.get(aggregateId).tailMap(fromVersion).values().stream().sorted(Comparator.comparing(Event::getVersion)).collect(Collectors.toList());
	}

}
