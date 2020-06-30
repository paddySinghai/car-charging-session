package com.everon.carchargingsession.dto;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/** In-Memory Cache Object for Storing Car charging sessions details */
public final class AppCache {
  private static final AppCache appCache = new AppCache();
  private final Cache<UUID, CarChargingDetailsDto> inProgressSessions;
  private final Cache<UUID, CarChargingDetailsDto> stoppedSessions;
  private final Map<UUID, CarChargingDetailsDto> carChargingDetailsMap;

  private AppCache() {
    carChargingDetailsMap = new ConcurrentHashMap<>();
    inProgressSessions = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
    stoppedSessions = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
  }

  public void clearCache(Map<UUID, CarChargingDetailsDto> carChargingDetailsMap) {
    synchronized (carChargingDetailsMap) {
      carChargingDetailsMap.clear();
    }
  }

  public static AppCache getAppCache() {
    return appCache;
  }

  public Map<UUID, CarChargingDetailsDto> getCarChargingDetailsMap() {
    return carChargingDetailsMap;
  }

  public Cache<UUID, CarChargingDetailsDto> getInProgressSessions() {
    return inProgressSessions;
  }

  public Cache<UUID, CarChargingDetailsDto> getStoppedSessions() {
    return stoppedSessions;
  }
}
