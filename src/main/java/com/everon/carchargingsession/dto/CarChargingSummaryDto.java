package com.everon.carchargingsession.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/** Dto class for Car Charging Session Summary */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarChargingSummaryDto {
  @ApiModelProperty(value = "Total number of submitted sessions")
  private long totalCount;

  @ApiModelProperty(value = "Number of active sessions")
  private long startedCount;

  @ApiModelProperty(value = "Number of terminated sessions")
  private long stoppedCount;
}
