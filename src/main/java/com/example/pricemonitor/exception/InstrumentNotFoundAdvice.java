package com.example.pricemonitor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InstrumentNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(InstrumentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String InstrumentNotFoundHandler(InstrumentNotFoundException ex) {
		return ex.getMessage();
	}
}