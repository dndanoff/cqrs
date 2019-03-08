package com.danoff.cqrs.demo.read;

import java.time.LocalDateTime;

public class FlightReadModel {
	private String departureAirport;
	private String arrivalAirport;
	private LocalDateTime departureTime;

	public FlightReadModel() {
		this(null, null, null);
	}

	public FlightReadModel(String departureAirport, String arrivalAirport, LocalDateTime departureTime) {
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureTime = departureTime;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	@Override
	public String toString() {
		return "FlightReadModel [departureAirport=" + departureAirport + ", arrivalAirport=" + arrivalAirport
				+ ", departureTime=" + departureTime + "]";
	}

}
