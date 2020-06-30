package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Interface class containing methods to add charging sessions in the cache */
public interface CarChargingSessionRepository {
  /**
   * Method to add sessions in the cache
   *
   * @param chargingSession - CarChargingDetailsDto - session object
   * @return chargingSession - CarChargingDetailsDto - Object stored in the cache
   */
  CarChargingDetailsDto save(CarChargingDetailsDto chargingSession);

  /**
   * Method to find session the cache by Id
   *
   * @param id - UUID - session ID
   * @return chargingSession - CarChargingDetailsDto - session object from the cache of present
   */
  Optional<CarChargingDetailsDto> findById(UUID id);

  /**
   * Method to fetch list of all the sessions
   *
   * @return sessionList - List &lt;CarChargingDetailsDto&gt; - List of all the submitted sessions
   */
  List<CarChargingDetailsDto> findAll();
}
