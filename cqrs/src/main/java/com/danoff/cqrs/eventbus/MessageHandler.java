package com.danoff.cqrs.eventbus;

public interface MessageHandler <T> {
	  void handle(Message<T> message);
}
