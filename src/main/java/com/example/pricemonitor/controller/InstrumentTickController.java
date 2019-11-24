package com.example.pricemonitor.controller;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pricemonitor.model.InstrumentTick;
import com.example.pricemonitor.repository.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.pricemonitor.exception.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
class InstrumentTickController {

	private final InstrumentTickRepository repository;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	InstrumentTickController(InstrumentTickRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/statistics")
	String getallStatistics() {
		List<InstrumentTick> listInstrumentTick = repository.findAll();

		if (listInstrumentTick.size() == 0) {
			throw new InstrumentNotFoundException();
		}
		HashMap<Long, Double> tickPriceMap = new HashMap<Long, Double>();
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

		long currentTime = currentTimestamp.getTime();
		long hashMapKey = 1;
		for (InstrumentTick instrumentTick : listInstrumentTick) {

			log.info("instrumentTick.getPrice " + instrumentTick.getPrice());
			log.info("instrumentTick.getTimestamp " + instrumentTick.getTimestamp());

			long incomingTickTimestamp = instrumentTick.getTimestamp();

			log.info("incomingTickTimestamp " + incomingTickTimestamp);
			log.info("currentTime  " + currentTime);
			double diff = currentTime - incomingTickTimestamp;

			log.info("timediff " + diff);
			if (diff < 60000) {

				tickPriceMap.put(hashMapKey, instrumentTick.getPrice());
			}
			hashMapKey++;
		}

		Double maxTickPriceInMap, minTickPriceInMap;
		// Double minTickPriceInMap;

		if (tickPriceMap.isEmpty()) {
			throw new InstrumentNotFoundException();
		} else {
			maxTickPriceInMap = (Collections.max(tickPriceMap.values()));
			minTickPriceInMap = (Collections.min(tickPriceMap.values()));
		}

		Double maxValue = (double) 0;
		Double minValue = (double) 0;
		log.info("Count of total tick of instrument in last 60 seconds " + tickPriceMap.size());
		int count = tickPriceMap.size();
		Double sumTickPrice = (double) 0;
		for (Entry<Long, Double> entry : tickPriceMap.entrySet()) {
			if (entry.getValue() == maxTickPriceInMap) {
				maxValue = entry.getValue();
				log.info("Max tick price in last 60 seconds" + entry.getValue());
			}
			if (entry.getValue() == minTickPriceInMap) {
				minValue = entry.getValue();
				log.info("Min tick price in last 60 seconds" + entry.getValue());
			}
			sumTickPrice += entry.getValue();
		}
		Double avg = (double) (sumTickPrice / count);

		Gson gsonBuilder = new GsonBuilder().create();

		Map<String, Number> tickStatistics = new HashMap<String, Number>();
		tickStatistics.put("avg", avg);
		tickStatistics.put("max", maxValue);
		tickStatistics.put("min", minValue);
		tickStatistics.put("count", count);

		String jsonFromJavaMap = gsonBuilder.toJson(tickStatistics);

		return jsonFromJavaMap;
	}

	@PostMapping("/ticks")
	String newInstrumentTick(@RequestBody InstrumentTick newInstrumentTick) {

		log.info("incoming tick TS: " + newInstrumentTick.getTimestamp());

		long incomingTickTimestamp = newInstrumentTick.getTimestamp();
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		long currentTime = currentTimestamp.getTime();

		log.info("incomingTickTimestamp " + incomingTickTimestamp);
		log.info("currentTime  " + currentTime);
		double diff = currentTime - incomingTickTimestamp;
		log.info("timediff " + diff);
		String returnCode = null;
		if (repository.save(newInstrumentTick) != null) {

			if (diff < 60000) {

				returnCode = newInstrumentTick.status(HttpStatus.CREATED);
			} else {
				returnCode = newInstrumentTick.noContentStatus(HttpStatus.NO_CONTENT);
			}
		}
		return returnCode;
	}

	@GetMapping("/statistics/{instrument_identifier}")
	String getStatisticsforInstrument(@PathVariable String instrument_identifier) {
		log.info("instrument_identifier :  " + instrument_identifier);
		List<InstrumentTick> listInstrumentTick = repository.findAll();
		if (listInstrumentTick.size() == 0) {
			throw new InstrumentNotFoundException(instrument_identifier);
		}

		HashMap<Long, Double> tickPriceMap = new HashMap<Long, Double>();
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

		long currentTime = currentTimestamp.getTime();
		long keyNo = 1;
		for (InstrumentTick instrumentTick : listInstrumentTick) {

			log.info("instrumentTick.getPrice " + instrumentTick.getPrice());
			log.info("instrumentTick.getTimestamp " + instrumentTick.getTimestamp());

			long incomingTickTimestamp = instrumentTick.getTimestamp();

			log.info("incomingTickTimestamp " + incomingTickTimestamp);
			log.info("currentTime  " + currentTime);
			double timeDiff = currentTime - incomingTickTimestamp;

			log.info("timediff " + timeDiff);
			String instrument = instrumentTick.getInstrument();
			if ((timeDiff < 60000) && (instrument.equals(instrument_identifier))) {
				log.info("Time difference less than 60 second with instrument_identifier " + timeDiff);
				tickPriceMap.put(keyNo, instrumentTick.getPrice());
			}
			keyNo++;
		}
		Double maxTickPriceInMap, minTickPriceInMap;
		// Double minTickPriceInMap;

		if (tickPriceMap.isEmpty()) {
			throw new InstrumentNotFoundException(instrument_identifier);
		} else {
			maxTickPriceInMap = (Collections.max(tickPriceMap.values()));
			minTickPriceInMap = (Collections.min(tickPriceMap.values()));
		}
		Double maxValue = (double) 0;
		Double minValue = (double) 0;
		log.info("Count of total tick of a given instrument in last 60 seconds " + tickPriceMap.size());
		int count = tickPriceMap.size();
		Double sumTickPrice = (double) 0;
		for (Entry<Long, Double> entry : tickPriceMap.entrySet()) {
			if (entry.getValue() == maxTickPriceInMap) {
				maxValue = entry.getValue();
				log.info("Max tick price in last 60 seconds for a given instrument " + entry.getValue());
			}
			if (entry.getValue() == minTickPriceInMap) {
				minValue = entry.getValue();
				log.info("Min tick price in last 60 seconds for a given instrument" + entry.getValue());
			}
			sumTickPrice += entry.getValue();
		}
		Double avg = (double) (sumTickPrice / count);
		log.info("avg   " + avg);

		Gson gsonBuilder = new GsonBuilder().create();

		Map<String, Number> tickStatistics = new HashMap<String, Number>();
		tickStatistics.put("avg", avg);
		tickStatistics.put("max", maxValue);
		tickStatistics.put("min", minValue);
		tickStatistics.put("count", count);

		String jsonFromJavaMap = gsonBuilder.toJson(tickStatistics);

		return jsonFromJavaMap;

	}

}
