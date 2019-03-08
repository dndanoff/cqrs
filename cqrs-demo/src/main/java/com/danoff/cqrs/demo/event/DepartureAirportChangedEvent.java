package com.danoff.cqrs.demo.event;

import java.time.LocalDateTime;

import com.danoff.cqrs.util.IdGenerator;

public class DepartureAirportChangedEvent implements IteneraryChangedEvent<String> {

	private final String id;
	private final String aggregateId;
	private final Long version;
	private final LocalDateTime createdAt;
	
	private final String data;
	
	public DepartureAirportChangedEvent(String aggregateId, Long version, String data) {
		super();
		this.id = IdGenerator.generateId();
		this.aggregateId = aggregateId;
		this.version = version;
		this.createdAt = LocalDateTime.now();
		this.data = data;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String getAggregateId() {
		return aggregateId;
	}

	@Override
	public String getData() {
		return data;
	}

}
