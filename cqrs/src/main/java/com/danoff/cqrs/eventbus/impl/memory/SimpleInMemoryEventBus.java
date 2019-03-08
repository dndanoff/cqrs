package com.danoff.cqrs.eventbus.impl.memory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.danoff.cqrs.core.DomainObject;
import com.danoff.cqrs.eventbus.Message;
import com.danoff.cqrs.eventbus.MessageHandler;
import com.danoff.cqrs.eventbus.impl.AbstractEventBus;

public class SimpleInMemoryEventBus<T extends DomainObject>  extends AbstractEventBus<T> {
	protected Map<String, List<MessageHandler<?>>> handlers;

	public SimpleInMemoryEventBus() {
		super();
		handlers = new ConcurrentHashMap<>();
	}

	@Override
	public void deliver(String address, Message<?> message) {
		dispatch(address, message);
	}

	@Override
	public Map<String, List<MessageHandler<?>>> getHandlers() {
		return handlers;
	}

}
