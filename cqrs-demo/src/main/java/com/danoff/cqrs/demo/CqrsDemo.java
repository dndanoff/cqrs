package com.danoff.cqrs.demo;

import java.time.LocalDateTime;

import com.danoff.cqrs.CQRS;
import com.danoff.cqrs.demo.command.BookFlightCommand;
import com.danoff.cqrs.demo.command.ChangeArrivalAirportCommand;
import com.danoff.cqrs.demo.command.ChangeDepartureAirportCommand;
import com.danoff.cqrs.demo.command.ChangeDepartureTimeCommand;
import com.danoff.cqrs.demo.domain.Flight;
import com.danoff.cqrs.demo.domain.FlightData;
import com.danoff.cqrs.demo.event.ArrivalAirportChangedEvent;
import com.danoff.cqrs.demo.event.DepartureAirportChangedEvent;
import com.danoff.cqrs.demo.event.DepartureTimeChangedEvent;
import com.danoff.cqrs.demo.event.FlightBookedEvent;
import com.danoff.cqrs.demo.read.FlightReadModel;
import com.danoff.cqrs.util.IdGenerator;

public class CqrsDemo {

	private static FlightReadModel readModel = new FlightReadModel();

	public static void main(String[] args) {
		CQRS cqrs = CQRS.get();
		cqrs.withDomain(Flight.class)
			.onCommand(BookFlightCommand.class, (command, aggregate) -> {
				if(command.getData().getDepartureAirport().equals(command.getData().getArrivalAirport())) {
					System.out.println("Invalid command. No event will be produced");
					return;
				}
				cqrs.storeEvent(new FlightBookedEvent(aggregate.getId(), aggregate.getVersion()+1,command.getData()));
			}).onCommand(ChangeDepartureAirportCommand.class, (command, aggregate) -> {
				if(command.getData().equals(aggregate.getState().getArrivalAirport())
						|| command.getData().equals(aggregate.getState().getDepartureAirport())) {
					System.out.println("Invalid command. No event will be produced");
					return;
				}
				cqrs.storeEvent(new DepartureAirportChangedEvent(aggregate.getId(), aggregate.getVersion()+1,command.getData()));
			}).onCommand(ChangeArrivalAirportCommand.class, (command, aggregate) -> {
				if(command.getData().equals(aggregate.getState().getDepartureAirport())
						|| command.getData().equals(aggregate.getState().getArrivalAirport())) {
					System.out.println("Invalid command. No event will be produced");
					return;
				}
				cqrs.storeEvent(new ArrivalAirportChangedEvent(aggregate.getId(), aggregate.getVersion()+1,command.getData()));
			}).onCommand(ChangeDepartureTimeCommand.class, (command, aggregate) -> {
				if(!command.getData().isAfter(LocalDateTime.now())) {
					System.out.println("Invalid command. No event will be produced");
					return;
				}
				cqrs.storeEvent(new DepartureTimeChangedEvent(aggregate.getId(), aggregate.getVersion()+1,command.getData()));
			}).onEvent(FlightBookedEvent.class, (e) -> {
				readModel.setDepartureAirport(e.getData().getDepartureAirport());
				readModel.setArrivalAirport(e.getData().getArrivalAirport());
				readModel.setDepartureTime(e.getData().getDepartureTime());
			}).onEvent(DepartureAirportChangedEvent.class, (e) -> {
				readModel.setDepartureAirport(e.getData());
			}).onEvent(ArrivalAirportChangedEvent.class, (e) -> {
				readModel.setArrivalAirport(e.getData());
			}).onEvent(DepartureTimeChangedEvent.class, (e) -> {
				readModel.setDepartureTime(e.getData());
			});
		
		
		String flightId = IdGenerator.generateId();
		cqrs.send(new BookFlightCommand(flightId, new FlightData("LBSF", "LBWN", LocalDateTime.now().plusDays(7))));
		
		System.out.println(readModel);
		
		cqrs.send(new ChangeDepartureAirportCommand(flightId, "LBWN"));
		
		System.out.println(readModel);
		
		cqrs.send(new ChangeArrivalAirportCommand(flightId, "EGLF"));
		
		System.out.println(readModel);
		
		System.out.println("Aggregate version: "+cqrs.withDomain(Flight.class).aggregateRoot(flightId).getVersion());
	}
}
