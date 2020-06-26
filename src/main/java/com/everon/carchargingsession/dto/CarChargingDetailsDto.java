package com.everon.carchargingsession.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class CarChargingDetailsDto {
  @ApiModelProperty(value = "UUID of the charging session")
  UUID id;

  @ApiModelProperty(value = "Name of the charging session")
  String stationId;

  @ApiModelProperty(value = "Start Time")
  LocalDateTime startedAt;

  @ApiModelProperty(value = "End Time")
  LocalDateTime stoppedAt;

  @ApiModelProperty(value = "Status of the session")
  StatusEnum status;
}
