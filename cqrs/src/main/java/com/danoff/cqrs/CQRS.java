package com.danoff.cqrs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.danoff.cqrs.core.BaseAggregateRoot;
import com.danoff.cqrs.core.Command;
import com.danoff.cqrs.core.DomainContext;
import com.danoff.cqrs.core.Event;
import com.danoff.cqrs.eventbus.EventBus;
import com.danoff.cqrs.eventbus.impl.memory.SimpleInMemoryEventBus;
import com.danoff.cqrs.eventstore.EventStoreRepository;
import com.danoff.cqrs.eventstore.impl.memory.SimpleEventStoreRepository;
import com.danoff.cqrs.eventstore.impl.memory.SimpleInMemoryEventStore;

public class CQRS {

	private static final CQRS INSTANCE = new CQRS();
	private volatile boolean initialized;
	private final Map<Class<?>, DomainContext<?>> domainContexts;

	private EventBus<Event<?>> eventBus;
	private EventBus<Command<?>> commandBus;
	private EventStoreRepository repository;

	private CQRS() {
		this.initialized = false;
		this.domainContexts = new ConcurrentHashMap<>();
	}

	public synchronized static CQRS init(EventBus<Event<?>> eventBus, EventBus<Command<?>> commandBus,
			EventStoreRepository repository) {
		if (!INSTANCE.initialized) {
			INSTANCE.eventBus = eventBus;
			INSTANCE.commandBus = commandBus;
			INSTANCE.repository = repository;

			INSTANCE.initialized = true;
		}

		return INSTANCE;
	}

	public synchronized static CQRS get() {
		if (!INSTANCE.initialized) {
			SimpleInMemoryEventStore eventStore = new SimpleInMemoryEventStore();
			SimpleInMemoryEventBus<Event<?>> eventBus = new SimpleInMemoryEventBus<>();
			SimpleInMemoryEventBus<Command<?>> commandBus = new SimpleInMemoryEventBus<>();
			SimpleEventStoreRepository repository = new SimpleEventStoreRepository(eventStore);

			return init(eventBus, commandBus, repository);
		}

		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseAggregateRoot<?>> DomainContext<T> withDomain(Class<T> type) {
		DomainContext<T> domainContext = (DomainContext<T>) domainContexts.get(type);
		if (domainContext == null) {
			domainContext = new DomainContext<T>(type, commandBus, eventBus, repository);
			domainContexts.put(type, domainContext);
		}
		return domainContext;
	}

	public void storeEvent(Event<?> event) {
		repository.saveEvent(event);
		eventBus.send(event.getClass().getName(), event);
	}

	public <T extends Command<?>> void send(T command) {
		if (command == null) {
			throw new IllegalArgumentException("The argument 'command' must not be null.");
		}

		commandBus.send(command.getClass().getName(), command);
	}

}
