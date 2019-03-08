package com.danoff.cqrs.demo.domain;

import com.danoff.cqrs.core.BaseAggregateRoot;
import com.danoff.cqrs.core.Event;
import com.danoff.cqrs.demo.event.ArrivalAirportChangedEvent;
import com.danoff.cqrs.demo.event.DepartureAirportChangedEvent;
import com.danoff.cqrs.demo.event.DepartureTimeChangedEvent;
import com.danoff.cqrs.demo.event.FlightBookedEvent;

public class Flight extends BaseAggregateRoot<FlightData>{

	public Flight(String id) {
		super(id);
	}

	public Flight(String id, Long version) {
		super(id, version);
	}

	public Flight(String id, Long version, FlightData state) {
		super(id, version, state);
	}
	
	@Override
	public Flight apply(Event<?> event) {
		if(event instanceof FlightBookedEvent) {
			FlightBookedEvent actualEvent = (FlightBookedEvent)event;
			return new Flight(actualEvent.getAggregateId(), actualEvent.getVersion(), actualEvent.getData());
		}else if(event instanceof DepartureAirportChangedEvent) {
			DepartureAirportChangedEvent actualEvent = (DepartureAirportChangedEvent)event;
			FlightData data = new FlightData(actualEvent.getData(), getState().getArrivalAirport(), getState().getDepartureTime());
			return new Flight(getId(), actualEvent.getVersion(), data);
		}else if(event instanceof ArrivalAirportChangedEvent) {
			ArrivalAirportChangedEvent actualEvent = (ArrivalAirportChangedEvent)event;
			FlightData data = new FlightData(getState().getDepartureAirport(), actualEvent.getData(), getState().getDepartureTime());
			return new Flight(getId(), actualEvent.getVersion(), data);
		}else if(event instanceof DepartureTimeChangedEvent) {
			DepartureTimeChangedEvent actualEvent = (DepartureTimeChangedEvent)event;
			FlightData data = new FlightData(getState().getDepartureAirport(), getState().getArrivalAirport(), actualEvent.getData());
			return new Flight(getId(), actualEvent.getVersion(), data);
		}
		
		return this;
	}

}
