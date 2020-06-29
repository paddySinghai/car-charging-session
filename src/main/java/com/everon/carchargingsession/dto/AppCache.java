package com.everon.carchargingsession.dto;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** In-Memory Cache Object for Storing Car charging sessions details */
public class AppCache {

  private Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();

  private static  AppCache appCache;

  private AppCache() {}

  public static AppCache getAppCache() {
    if (null == appCache) {
      return appCache = new AppCache();
    }
    return appCache;
  }

  public Map<UUID, CarChargingDetailsDto> getCarChargingDetailsMap() {
    return carChargingDetailsMap;
  }

  public void setCarChargingDetailsMap(Map<UUID, CarChargingDetailsDto> carChargingDetailsMap) {
    this.carChargingDetailsMap = carChargingDetailsMap;
  }
}
