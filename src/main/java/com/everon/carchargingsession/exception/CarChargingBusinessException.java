package com.everon.carchargingsession.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Exception class for Car Charging Session */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarChargingBusinessException extends Exception {
  private String errorCode;
  private String errorMessage;
}
