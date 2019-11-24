package com.example.pricemonitor.exception;

@SuppressWarnings("serial")
public class InstrumentNotFoundException extends RuntimeException {

	public InstrumentNotFoundException(String id) {
		super("No data found for instrument " + id + " in last 60 seconds ");
	}

	public InstrumentNotFoundException() {
		super("No data found for ticks across all instruments in last 60 seconds ");
	}
}