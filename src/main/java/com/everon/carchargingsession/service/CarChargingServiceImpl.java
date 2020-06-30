package com.everon.carchargingsession.service;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.CarChargingSummaryDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import com.everon.carchargingsession.repository.CarChargingSessionRepository;
import com.everon.carchargingsession.repository.CarSessionSummaryRepository;
import com.everon.carchargingsession.util.HelperUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for CarChargingService Interface containing methods to start,stop,retrieve
 * session details and summary for various car charging sessions
 */
@Service
public class CarChargingServiceImpl implements CarChargingService {
  private final CarChargingSessionRepository carChargingSessionRepository;
  private final CarSessionSummaryRepository carSessionSummaryRepository;

  public CarChargingServiceImpl(
      CarChargingSessionRepository carChargingSessionRepository,
      CarSessionSummaryRepository carSessionSummaryRepository) {
    this.carChargingSessionRepository = carChargingSessionRepository;
    this.carSessionSummaryRepository = carSessionSummaryRepository;
  }

  @Override
  public CarChargingDetailsDto submitNewChargingSession(String stationId) {
    CarChargingDetailsDto carChargingDetailsDto =
        HelperUtil.prepareChargingSessionDetailsInput(stationId);
    carSessionSummaryRepository.updateInProgressSessions(carChargingDetailsDto);
    return carChargingSessionRepository.save(carChargingDetailsDto);
  }

  @Override
  public CarChargingDetailsDto stopChargingSession(UUID id) throws CarChargingBusinessException {

    Optional<CarChargingDetailsDto> carChargingDetailsDto =
        carChargingSessionRepository.findById(id);
    carChargingDetailsDto.orElseThrow(
        () ->
            new CarChargingBusinessException(
                "SESSION_NOT_FOUND", "No Active session found for id: " + id));

    if (!StatusEnum.IN_PROGRESS.equals(carChargingDetailsDto.get().getStatus())) {
      throw new CarChargingBusinessException(
          "SESSION_ALREADY_TERMINATED", "Session already Terminated for id: " + id);
    }

    carChargingDetailsDto.get().setStatus(StatusEnum.FINISHED);
    carChargingDetailsDto.get().setStoppedAt(LocalDateTime.now());

    carSessionSummaryRepository.updateStoppedSessions(carChargingDetailsDto.get());
    carChargingSessionRepository.save(carChargingDetailsDto.get());

    return carChargingDetailsDto.get();
  }

  @Override
  public Collection<CarChargingDetailsDto> retrieveAllChargingSession() {

    return carChargingSessionRepository.findAll();
  }

  @Override
  public CarChargingSummaryDto retrieveSummaryOfChargingSession() {

    long inProgressSessionCount = carSessionSummaryRepository.getInProgressSessionCount();
    long stoppedCount = carSessionSummaryRepository.getStoppedSessionCount();

    CarChargingSummaryDto carChargingSummaryDto =
        CarChargingSummaryDto.builder()
            .totalCount(inProgressSessionCount + stoppedCount)
            .stoppedCount(stoppedCount)
            .startedCount(inProgressSessionCount)
            .build();
    return carChargingSummaryDto;
  }
}
