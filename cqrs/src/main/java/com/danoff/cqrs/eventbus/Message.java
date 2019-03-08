package com.danoff.cqrs.eventbus;

import java.util.HashMap;
import java.util.Map;

import com.danoff.cqrs.util.IdGenerator;

public class Message<T> {
	public static final String HEADER_MESSAGE_ADDR = "HEADER_MESSAGE_ADDRESS";

	private String id;
	private String aggregateId;
	private Class<T> type;
	private T payload;
	private Map<String, Object> headers;

	public Message(Class<T> type, T payload, String aggregateId) {
		super();
		this.id = IdGenerator.generateId();
		this.aggregateId = aggregateId;
		this.type = type;
		this.payload = payload;
		this.headers = new HashMap<>();
	}

	public Message<T> addHeader(String key, Object value) {
		headers.put(key, value);
		return this;
	}

	public Object getHeader(String key) {
		return headers.get(key);
	}

	public Message<T> addAddressHeader(Object value) {
		headers.put(HEADER_MESSAGE_ADDR, value);
		return this;
	}

	public String getAddress() {
		return (String) headers.get(HEADER_MESSAGE_ADDR);
	}

	public T getPayload() {
		return payload;
	}

	public String getId() {
		return id;
	}
	
	public String getAggregateId() {
		return aggregateId;
	}

	public Class<T> getType() {
		return type;
	}
}
