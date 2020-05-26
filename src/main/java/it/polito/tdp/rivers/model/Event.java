package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event {
	public enum EventType {
		NUOVO_GIORNO, TRACIMAZIONE
	}
	
	private EventType type ;
	private LocalDate day ;
}
