package com.danoff.cqrs.demo.command;

import java.time.LocalDateTime;

import com.danoff.cqrs.core.Command;

public class ChangeArrivalAirportCommand implements Command<String>{

	private final String airport;
	private final String flightId;
	private final LocalDateTime created;
	
	public ChangeArrivalAirportCommand(String flightId, String airport) {
		this.flightId = flightId;
		this.airport = airport;
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
	public String getData() {
		return airport;
	}

}
