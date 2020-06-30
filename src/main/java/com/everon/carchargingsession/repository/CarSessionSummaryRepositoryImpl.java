package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class CarSessionSummaryRepositoryImpl implements CarSessionSummaryRepository {
  private final Cache<UUID, CarChargingDetailsDto> inProgressSessions;
  private final Cache<UUID, CarChargingDetailsDto> stoppedSessions;

  public CarSessionSummaryRepositoryImpl() {
    inProgressSessions = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
    stoppedSessions = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
  }

  @Override
  public void updateInProgressSessions(final CarChargingDetailsDto chargingSession) {
    Objects.requireNonNull(chargingSession);
    if (StatusEnum.IN_PROGRESS.equals(chargingSession.getStatus())) {
      inProgressSessions.put(chargingSession.getId(), chargingSession);
    }
  }

  @Override
  public void updateStoppedSessions(final CarChargingDetailsDto chargingSession) {
    Objects.requireNonNull(chargingSession);
    stoppedSessions.put(chargingSession.getId(), chargingSession);
    inProgressSessions.invalidate(chargingSession.getId());
  }

  @Override
  public long getInProgressSessionCount() {
    return inProgressSessions.size();
  }

  @Override
  public long getStoppedSessionCount() {
    return stoppedSessions.size();
  }
}
