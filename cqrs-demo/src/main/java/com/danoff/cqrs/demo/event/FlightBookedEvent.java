package com.danoff.cqrs.demo.event;

import java.time.LocalDateTime;

import com.danoff.cqrs.core.Event;
import com.danoff.cqrs.demo.domain.FlightData;
import com.danoff.cqrs.util.IdGenerator;

public class FlightBookedEvent implements Event<FlightData> {

	private final String id;
	private final String aggregateId;
	private final Long version;
	private final LocalDateTime createdAt;
	
	private final FlightData data;
	
	public FlightBookedEvent(String aggregateId, Long version, FlightData data) {
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
	public FlightData getData() {
		return data;
	}

}
