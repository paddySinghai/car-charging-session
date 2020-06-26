package com.everon.carchargingsession.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/** Dto containing details for Car charging session */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarChargingDetailsDto extends GenericErrorDto {
  UUID id;
  String stationId;
  LocalDateTime startedAt;
  LocalDateTime stoppedAt;
  StatusEnum status;
}
