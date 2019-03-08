package com.danoff.cqrs.demo.domain;

import java.time.LocalDateTime;

public class FlightData {
	private final String departureAirport;
	private final String arrivalAirport;
	private final LocalDateTime departureTime;

	public FlightData(String departureAirport, String arrivalAirport, LocalDateTime departureTime) {
		super();
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureTime = departureTime;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
}
