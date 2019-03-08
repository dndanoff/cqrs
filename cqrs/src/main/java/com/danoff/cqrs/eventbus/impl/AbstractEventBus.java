package com.danoff.cqrs.eventbus.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.danoff.cqrs.core.DomainObject;
import com.danoff.cqrs.eventbus.EventBus;
import com.danoff.cqrs.eventbus.Message;
import com.danoff.cqrs.eventbus.MessageHandler;

public abstract class AbstractEventBus<T extends DomainObject> implements EventBus<T> {

	private static final Logger logger = Logger.getLogger(AbstractEventBus.class.getName());

	public abstract void deliver(String address, Message<?> message);
	public abstract Map<String, List<MessageHandler<?>>> getHandlers();

	@Override
	public void send(String address, T message) {
		logger.log(Level.INFO, "Send message at address={0}", address);
		deliver(address, buildMessage(address, message));
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void subscribe(String address, MessageHandler handler) {
		if (!getHandlers().containsKey(address)) {
			getHandlers().put(address, new ArrayList<>());
		}
		getHandlers().get(address).add(handler);
	}

	@Override
	public void unsubscribe(String address) {
		getHandlers().remove(address);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void unsubscribe(String address, MessageHandler handler) {
		getHandlers().get(address).remove(handler);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void dispatch(String address, Message message) {

		List<MessageHandler<?>> registeredHandlers = getHandlers().get(address);

		if (registeredHandlers != null) {
			registeredHandlers.stream().forEach(h -> h.handle(message));
		} else {
			logger.log(Level.INFO, "No registered handlers for message address={0}", message.getAddress());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Message<?> buildMessage(String address, T message) {
		return new Message(message.getClass(), message, message.getAggregateId()).addHeader(Message.HEADER_MESSAGE_ADDR, address);
	}
}
