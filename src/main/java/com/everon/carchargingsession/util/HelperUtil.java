package com.everon.carchargingsession.util;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

/** Util class for Car Charging session service */
public class HelperUtil {
  /**
   * Method to prepare details of Car Charging Session
   *
   * @param stationId - String - Input Station Id
   * @return carChargingDetailsDto - CarChargingDetailsDto - Dto containing details of given
   *     charging session
   */
  public static CarChargingDetailsDto prepareChargingSessionDetailsInput(String stationId) {
    return CarChargingDetailsDto.builder()
        .id(UUID.randomUUID())
        .stationId(stationId)
        .startedAt(LocalDateTime.now())
        .status(StatusEnum.IN_PROGRESS)
        .build();
  }
}
