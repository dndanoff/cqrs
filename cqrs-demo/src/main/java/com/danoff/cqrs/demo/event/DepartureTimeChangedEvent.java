package com.danoff.cqrs.demo.event;

import java.time.LocalDateTime;

import com.danoff.cqrs.util.IdGenerator;

public class DepartureTimeChangedEvent implements IteneraryChangedEvent<LocalDateTime> {

	private final String id;
	private final String aggregateId;
	private final Long version;
	private final LocalDateTime createdAt;
	
	private final LocalDateTime data;
	
	public DepartureTimeChangedEvent(String aggregateId, Long version, LocalDateTime data) {
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
	public LocalDateTime getData() {
		return data;
	}

}
