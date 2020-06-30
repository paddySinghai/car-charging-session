package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.AppCache;
import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.google.common.cache.Cache;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CarSessionSummaryRepositoryImpl implements CarSessionSummaryRepository {
  private final Cache<UUID, CarChargingDetailsDto> inProgressSessions;
  private final Cache<UUID, CarChargingDetailsDto> stoppedSessions;

  public CarSessionSummaryRepositoryImpl() {
    inProgressSessions = AppCache.getAppCache().getInProgressSessions();
    stoppedSessions = AppCache.getAppCache().getStoppedSessions();
  }

  @Override
  public void updateInProgressSessions(final CarChargingDetailsDto chargingSession) {

    if (StatusEnum.IN_PROGRESS.equals(chargingSession.getStatus())) {
      inProgressSessions.put(chargingSession.getId(), chargingSession);
    }
  }

  @Override
  public void updateStoppedSessions(final CarChargingDetailsDto chargingSession) {

    inProgressSessions.invalidate(chargingSession.getId());
    stoppedSessions.put(chargingSession.getId(), chargingSession);
  }

  @Override
  public long getInProgressSessionCount() {

    inProgressSessions.cleanUp();
    return inProgressSessions.size();
  }

  @Override
  public long getStoppedSessionCount() {

    stoppedSessions.cleanUp();
    return stoppedSessions.size();
  }
}
