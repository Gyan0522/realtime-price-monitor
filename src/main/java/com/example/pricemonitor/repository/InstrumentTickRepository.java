package com.example.pricemonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pricemonitor.model.*;

public interface InstrumentTickRepository extends JpaRepository<InstrumentTick, Long> {

}