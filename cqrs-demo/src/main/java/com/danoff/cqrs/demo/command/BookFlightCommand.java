package com.danoff.cqrs.demo.command;

import java.time.LocalDateTime;

import com.danoff.cqrs.core.Command;
import com.danoff.cqrs.demo.domain.FlightData;

public class BookFlightCommand implements Command<FlightData>{

	private final FlightData flightData;
	private final String flightId;
	private final LocalDateTime created;
	
	public BookFlightCommand(String flightId, FlightData flightData) {
		this.flightId = flightId;
		this.flightData = flightData;
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
	public FlightData getData() {
		return flightData;
	}
}
