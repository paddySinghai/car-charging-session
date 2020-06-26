package com.everon.carchargingsession.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** In-Memory Cache Object for Storing Car charging sessions details */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class AppCache {

  private Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
}
