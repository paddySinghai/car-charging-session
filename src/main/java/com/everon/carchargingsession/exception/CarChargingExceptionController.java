package com.everon.carchargingsession.exception;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CarChargingExceptionController {
  @ExceptionHandler(value = CarChargingBusinessException.class)
  public ResponseEntity<CarChargingDetailsDto> exception(CarChargingBusinessException exception) {
    CarChargingDetailsDto carChargingDetailsDto = new CarChargingDetailsDto();
    carChargingDetailsDto.setErrorCode(exception.getErrorCode());
    carChargingDetailsDto.setErrorMessage(exception.getErrorMessage());
    return ResponseEntity.badRequest().body(carChargingDetailsDto);
  }
}
