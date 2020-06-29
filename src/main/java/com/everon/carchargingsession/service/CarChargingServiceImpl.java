package com.everon.carchargingsession.service;

import com.everon.carchargingsession.dto.AppCache;
import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.CarChargingSummaryDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import com.everon.carchargingsession.util.HelperUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for CarChargingService Interface containing methods to start,stop,retrieve
 * session details and summary for various car charging sessions
 */
@Service
public class CarChargingServiceImpl implements CarChargingService {
  AppCache appCache = AppCache.getAppCache();

  @Override
  public CarChargingDetailsDto submitNewChargingSession(String stationId)
      throws CarChargingBusinessException {

    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = appCache.getCarChargingDetailsMap();
    CarChargingDetailsDto carChargingDetailsDto =
        HelperUtil.prepareChargingSessionDetailsInput(stationId);

    carChargingDetailsMap.put(carChargingDetailsDto.getId(), carChargingDetailsDto);

    return carChargingDetailsDto;
  }

  @Override
  public CarChargingDetailsDto stopChargingSession(UUID id) throws CarChargingBusinessException {

    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = appCache.getCarChargingDetailsMap();

    if (!carChargingDetailsMap.containsKey(id)) {
      throw new CarChargingBusinessException(
          "SESSION_NOT_FOUND", "No Active session found for id: " + id);
    }
    CarChargingDetailsDto carChargingDetailsDto = carChargingDetailsMap.get(id);
    if (null != carChargingDetailsDto
        && StatusEnum.FINISHED.equals(carChargingDetailsDto.getStatus())) {
      throw new CarChargingBusinessException(
          "SESSION_ALREADY_TERMINATED", "Session already Terminated for id: " + id);
    }
    carChargingDetailsDto.setStatus(StatusEnum.FINISHED);
    carChargingDetailsDto.setStoppedAt(LocalDateTime.now());

    appCache.getCarChargingDetailsMap().replace(id, carChargingDetailsDto);
    return carChargingDetailsDto;
  }

  @Override
  public List<CarChargingDetailsDto> retrieveAllChargingSession()
      throws CarChargingBusinessException {
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = appCache.getCarChargingDetailsMap();
    if (null == carChargingDetailsMap || carChargingDetailsMap.isEmpty()) {
      throw new CarChargingBusinessException("NO_SUBMITTED_SESSIONS", "No sessions found!");
    }
    List<CarChargingDetailsDto> chargingDetailsDtoList =
        carChargingDetailsMap.values().stream().collect(Collectors.toList());
    return chargingDetailsDtoList;
  }

  @Override  public CarChargingSummaryDto retrieveSummaryOfChargingSession()
      throws CarChargingBusinessException {
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = appCache.getCarChargingDetailsMap();
    if (null == carChargingDetailsMap || carChargingDetailsMap.isEmpty()) {
      throw new CarChargingBusinessException("NO_SUBMITTED_SESSIONS", "No sessions found!");
    }

    long totalCount = carChargingDetailsMap.size();
    long stoppedCount =
        carChargingDetailsMap.entrySet().stream()
            .filter(x -> x.getValue().getStatus().equals(StatusEnum.FINISHED))
            .count();

    CarChargingSummaryDto carChargingSummaryDto =
        CarChargingSummaryDto.builder()
            .totalCount(totalCount)
            .stoppedCount(stoppedCount)
            .startedCount(totalCount - stoppedCount)
            .build();
    return carChargingSummaryDto;
  }
}
