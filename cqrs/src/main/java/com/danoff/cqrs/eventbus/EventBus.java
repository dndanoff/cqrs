package com.danoff.cqrs.eventbus;

import com.danoff.cqrs.core.DomainObject;

public interface EventBus <T extends DomainObject> {
	  void send(String address, T message);
	  void subscribe(String address, MessageHandler<?> handler);
	  void unsubscribe(String address);
	  void unsubscribe(String address, MessageHandler<?> handler);
}
