package com.danoff.cqrs.demo.command;

import java.time.LocalDateTime;

import com.danoff.cqrs.core.Command;

public class ChangeDepartureTimeCommand implements Command<LocalDateTime>{

	private final LocalDateTime departureDate;
	private final String flightId;
	private final LocalDateTime created;
	
	public ChangeDepartureTimeCommand(String flightId, LocalDateTime departureDate) {
		this.flightId = flightId;
		this.departureDate = departureDate;
		this.created = LocalDateTime.now();
	}
	
	@Override
	public String getAggregateId() {
		return flightId;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return created;
	}

	@Override
	public LocalDateTime getData() {
		return departureDate;
	}

}
