package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Interface class containing methods to add charging sessions in the cache */
public interface CarChargingSessionRepository {

  CarChargingDetailsDto save(final CarChargingDetailsDto chargingSession);

  Optional<CarChargingDetailsDto> findById(final UUID id);

  List<CarChargingDetailsDto> findAll();
}
