package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
/** Interface class containing methods to add charging sessions to the guava cache */
public interface CarSessionSummaryRepository {
  /**
   * Method to add ongoing session object in the guava cache
   *
   * @param chargingSession - CarChargingDetailsDto - session Object
   */
  void updateInProgressSessions(CarChargingDetailsDto chargingSession);
  /**
   * Method to add stopped/terminated session object in the guava cache
   *
   * @param chargingSession - CarChargingDetailsDto - session Object
   */
  void updateStoppedSessions(CarChargingDetailsDto chargingSession);

  /**
   * Method to get count of ongoing sessions in last 1min
   *
   * @return count - long - count of ongoing sessions in last 1min
   */
  long getInProgressSessionCount();

  /**
   * Method to get count of stopped/terminated sessions in last 1min
   *
   * @return count - long - count of stopped/terminated sessions in last 1min
   */
  long getStoppedSessionCount();
}
