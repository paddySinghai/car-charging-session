package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CarChargingSessionRepositoryImpl implements CarChargingSessionRepository {
  private final Map<UUID, CarChargingDetailsDto> chargingSessions;

  public CarChargingSessionRepositoryImpl() {
    chargingSessions = new ConcurrentHashMap<>();
  }

  @Override
  public CarChargingDetailsDto save(final CarChargingDetailsDto chargingSession) {

    Objects.requireNonNull(chargingSession);

    chargingSessions.put(chargingSession.getId(), chargingSession);

    return chargingSession;
  }

  @Override
  public Optional<CarChargingDetailsDto> findById(final UUID id) {

    Objects.requireNonNull(id);

    return Optional.ofNullable(chargingSessions.get(id));
  }

  @Override
  public List<CarChargingDetailsDto> findAll() {

    return new ArrayList<>(chargingSessions.values());
  }
}
