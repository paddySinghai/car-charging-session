package com.everon.carchargingsession.exception;

import com.everon.carchargingsession.dto.GenericErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Exception controller for Car charging session Module */
@ControllerAdvice
public class CarChargingExceptionController {
  @ExceptionHandler(value = CarChargingBusinessException.class)
  public ResponseEntity<GenericErrorDto> exception(CarChargingBusinessException exception) {
    GenericErrorDto genericErrorDto = new GenericErrorDto();
    genericErrorDto.setErrorCode(exception.getErrorCode());
    genericErrorDto.setErrorMessage(exception.getErrorMessage());
    return ResponseEntity.badRequest().body(genericErrorDto);
  }
}
