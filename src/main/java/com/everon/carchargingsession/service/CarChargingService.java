package com.everon.carchargingsession.service;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.exception.CarChargingBusinessException;

import java.util.List;
import java.util.UUID;

public interface CarChargingService {
  /**
   * Method to Submit a new charging session for the station
   *
   * @param stationId - String - Station Id of new charging session
   * @return carChargingDetailsDto - CarChargingDetailsDto - Object of CarChargingDetailsDto
   *     containing charging session details
   * @throws CarChargingBusinessException throws CarChargingBusinessException
   */
  public CarChargingDetailsDto submitNewChargingSession(String stationId)
      throws CarChargingBusinessException;

  /**
   * Method to Stop charging session
   *
   * @param id - UUID - UUID of the charging session
   * @return carChargingDetailsDto - CarChargingDetailsDto - Object of CarChargingDetailsDto
   *     containing updated Status of charging session
   * @throws CarChargingBusinessException throws link CarChargingBusinessException
   */
  public CarChargingDetailsDto stopChargingSession(UUID id) throws CarChargingBusinessException;

  /**
   * Method to Retrieve all charging sessions
   *
   * @return carChargingDetailsList - List<CarChargingDetailsDto> - List of All the charging
   *     sessions with details
   * @throws CarChargingBusinessException throws CarChargingBusinessException
   */
  public List<CarChargingDetailsDto> retrieveAllChargingSession()
      throws CarChargingBusinessException;

  /**
   * Method to Retrieve summary of submitted charging sessions
   *
   * @return summary of Submitted charging sessions
   * @throws CarChargingBusinessException throws CarChargingBusinessException
   */
  public Object retrieveSummaryOfChargingSession() throws CarChargingBusinessException;
}
