package com.danoff.cqrs.core;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.danoff.cqrs.eventbus.EventBus;
import com.danoff.cqrs.eventstore.EventStoreRepository;

public class DomainContext<T extends BaseAggregateRoot<?>> {
	private final EventBus<Event<?>> eventBus;
	private final EventBus<Command<?>> commandBus;
	private final EventStoreRepository repository;
	private final Class<T> domainType;

	public DomainContext(Class<T> domainType, EventBus<Command<?>> commandBus, EventBus<Event<?>> eventBus,
			EventStoreRepository repository) {
		super();
		this.eventBus = eventBus;
		this.domainType = domainType;
		this.commandBus = commandBus;
		this.repository = repository;
	}

	public T aggregateRoot(String aggregateId) {
		return repository.getRehydratedAggregate(domainType, aggregateId);
	}

	public <V extends Command<?>> DomainContext<T> onCommand(Class<V> commandType, BiConsumer<V, T> consumer) {
		if (commandType == null) {
			throw new IllegalArgumentException("The argument 'commandType' must to be assign.");
		}

		commandBus.subscribe(commandType.getName(), message -> {
			if (!commandType.isInstance(message.getPayload())) {
				throw new IllegalStateException("Message is not the specified command");
			}

			V command = commandType.cast(message.getPayload());
			T aggregateRoot = aggregateRoot(command.getAggregateId());
			consumer.accept(command, aggregateRoot);
		});

		return this;
	}

	public <V extends Event<?>> DomainContext<T> onEvent(Class<V> eventType, Consumer<V> consumer) {
		if (eventType == null) {
			throw new IllegalArgumentException("The argument 'eventType' must to be assign.");
		}

		eventBus.subscribe(eventType.getName(), message -> {
			if (!eventType.isInstance(message.getPayload())) {
				throw new IllegalStateException("Message is not the specific event");
			}

			V event = eventType.cast(message.getPayload());
			consumer.accept(event);
		});

		return this;
	}
}
