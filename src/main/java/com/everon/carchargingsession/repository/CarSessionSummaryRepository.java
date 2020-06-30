package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
/** Interface class containing methods to add charging sessions to the guava cache */
public interface CarSessionSummaryRepository {

  void updateInProgressSessions(final CarChargingDetailsDto chargingSession);

  void updateStoppedSessions(final CarChargingDetailsDto chargingSession);

  long getInProgressSessionCount();

  long getStoppedSessionCount();

}
