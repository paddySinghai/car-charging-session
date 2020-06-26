package com.everon.carchargingsession.controller;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.CarChargingSummaryDto;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import com.everon.carchargingsession.service.CarChargingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chargingSessions")
public class CarChargingController {

  private final CarChargingService carChargingService;

  @Autowired
  public CarChargingController(CarChargingService carChargingService) {
    this.carChargingService = carChargingService;
  }

  @ApiOperation(value = "Submit a new charging session for the station")
  @PostMapping(
      headers = "Accept=application/json",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public CarChargingDetailsDto submitNewChargingSession(@RequestBody String stationId)
      throws CarChargingBusinessException {
    return carChargingService.submitNewChargingSession(stationId);
  }

  @ApiOperation(value = "Stop charging session")
  @PutMapping(
      value = "/{id}",
      headers = "Accept=application/json",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public CarChargingDetailsDto stopChargingSession(@PathVariable UUID id)
      throws CarChargingBusinessException {
    return carChargingService.stopChargingSession(id);
  }

  @ApiOperation(value = "Retrieve all charging sessions")
  @GetMapping(
      headers = "Accept=application/json",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public List<CarChargingDetailsDto> retrieveAllChargingSession()
      throws CarChargingBusinessException {
    return carChargingService.retrieveAllChargingSession();
  }

  @ApiOperation(value = "Retrieve summary of submitted charging sessions")
  @GetMapping(
      value = "/summary",
      headers = "Accept=application/json",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public CarChargingSummaryDto retrieveSummaryOfChargingSession()
      throws CarChargingBusinessException {
    return carChargingService.retrieveSummaryOfChargingSession();
  }
}
