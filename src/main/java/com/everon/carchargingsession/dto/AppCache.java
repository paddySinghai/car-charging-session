package com.everon.carchargingsession.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** In-Memory Cache Object for Storing Car charging sessions details */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class AppCache {

  private Map<String, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
}
