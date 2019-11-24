package com.example.pricemonitor.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.http.HttpStatus;

@Data
@Entity
public class InstrumentTick {

	private @Id @GeneratedValue Long id;
	private String instrument;
	private Double price;
	private long timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	InstrumentTick() {
	}

	public InstrumentTick(String string, Double string2, long string3) {
		this.instrument = string;
		this.price = string2;
		this.timestamp = string3;

	}

	public static String status(HttpStatus CREATED) {
		// TODO Auto-generated method stub
		final String successCode = "201";
		return successCode;
	}

	public static String noContentStatus(HttpStatus NO_CONTENT) {
		// TODO Auto-generated method stub
		final String successCode = "204";
		return successCode;
	}

}