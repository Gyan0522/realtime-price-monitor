package com.example.pricemonitor.repository;



import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.pricemonitor.model.InstrumentTick;

@Configuration
@Slf4j
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(InstrumentTickRepository repository) {
    return args -> {
     // log.info("Preloading " + repository.save(new InstrumentTick("IBM.N", "143", "1478192204000")));
     // log.info("Preloading " + repository.save(new InstrumentTick("TGP.N", "148", "1478192204000")));
    };
  }
}