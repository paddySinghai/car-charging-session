package com.everon.carchargingsession.service;

import com.everon.carchargingsession.dto.AppCache;
import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.CarChargingSummaryDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Test class for CarChargingService */
@ExtendWith(MockitoExtension.class)
public class CarChargingServiceTest {
  @Mock AppCache appCache;
  @InjectMocks CarChargingServiceImpl carChargingService;

  @DisplayName("Test method to submit a new charging session")
  @Test
  public void testSubmitNewChargingSession() {
    // Arrange
    String stationId = "ABC-12345";
    CarChargingDetailsDto actualValue = prepareInput();
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    carChargingDetailsMap.put(actualValue.getId(), actualValue);
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act
    CarChargingDetailsDto outputDto = carChargingService.submitNewChargingSession(stationId);
    // Assert
    Assertions.assertEquals(actualValue.getStationId(), outputDto.getStationId());
    Assertions.assertEquals(actualValue.getStatus(), outputDto.getStatus());
    Assertions.assertNotNull(outputDto);
  }

  @DisplayName("Test method to stop charging Session")
  @Test
  public void testSopChargingSession() {
    // Arrange
    CarChargingDetailsDto actualValue = prepareInput();
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    carChargingDetailsMap.put(actualValue.getId(), actualValue);
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act
    CarChargingDetailsDto outputDto =
        carChargingService.stopChargingSession(
            UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18"));
    // Assert
    Assertions.assertEquals(actualValue.getStationId(), outputDto.getStationId());
    Assertions.assertEquals(StatusEnum.FINISHED, outputDto.getStatus());
    Assertions.assertEquals(actualValue.getId(), outputDto.getId());
    Assertions.assertNotNull(outputDto);
  }

  @DisplayName("Test method to stop charging Session with Status Finished")
  @Test
  public void testSopChargingSessionWithStatusFinished() throws CarChargingBusinessException {
    // Arrange
    CarChargingDetailsDto actualValue = prepareInput();
    actualValue.setStatus(StatusEnum.FINISHED);
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    carChargingDetailsMap.put(actualValue.getId(), actualValue);
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act & Assert
    Assertions.assertThrows(
        CarChargingBusinessException.class,
        () ->
            carChargingService.stopChargingSession(
                UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18")),
        "Session already Terminated for id: d9bb7458-d5d9-4de7-87f7-7f39edd51d18 ");
  }

  @DisplayName("Test method to stop charging Session with Wrong Id")
  @Test
  public void testSopChargingSessionWithWrongId() throws CarChargingBusinessException {
    // Arrange
    CarChargingDetailsDto actualValue = prepareInput();
    actualValue.setStatus(StatusEnum.FINISHED);
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    carChargingDetailsMap.put(actualValue.getId(), actualValue);
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act & Assert
    Assertions.assertThrows(
        CarChargingBusinessException.class,
        () ->
            carChargingService.stopChargingSession(
                UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d56")),
        "No Active session found for id: d9bb7458-d5d9-4de7-87f7-7f39edd51d56");
  }

  @DisplayName("Test Method to Retrieve all charging sessions")
  @Test
  public void retrieveAllChargingSession() {
    // Arrange
    CarChargingDetailsDto actualValue = prepareInput();
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    carChargingDetailsMap.put(actualValue.getId(), actualValue);
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act
    List<CarChargingDetailsDto> outputList = carChargingService.retrieveAllChargingSession();
    // Assert
    Assertions.assertEquals(actualValue.getStationId(), outputList.get(0).getStationId());
    Assertions.assertEquals(actualValue.getStatus(), outputList.get(0).getStatus());
    Assertions.assertNotNull(outputList);
  }

  @DisplayName("Test Method to Retrieve all charging sessions with No Submitted Sessions")
  @Test
  public void retrieveAllChargingSessionWithNoSubmittedSessions() {
    // Arrange
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act & Assert
    Assertions.assertThrows(
        CarChargingBusinessException.class,
        () -> carChargingService.retrieveAllChargingSession(),
        "No sessions found!");
  }

  @DisplayName("Test method to retrieve summary of Charging Sessions")
  @Test
  public void testRetrieveSummaryChargingSession() throws CarChargingBusinessException {
    // Arrange
    CarChargingSummaryDto carChargingSummaryDto =
        CarChargingSummaryDto.builder().totalCount(1).startedCount(1).stoppedCount(0).build();
    CarChargingDetailsDto actualValue = prepareInput();
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    carChargingDetailsMap.put(actualValue.getId(), actualValue);
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act
    CarChargingSummaryDto outputDto = carChargingService.retrieveSummaryOfChargingSession();
    // Assert
    Assertions.assertEquals(carChargingSummaryDto.getTotalCount(), outputDto.getTotalCount());
    Assertions.assertEquals(carChargingSummaryDto.getStartedCount(), outputDto.getStartedCount());
    Assertions.assertNotNull(carChargingSummaryDto);
  }

  @DisplayName("Test method to retrieve summary of Charging Sessions with No Submitted Sessions")
  @Test
  public void testRetrieveSummaryChargingSessionWithNoSubmittedSessions()
      throws CarChargingBusinessException {
    // Arrange
    Map<UUID, CarChargingDetailsDto> carChargingDetailsMap = new ConcurrentHashMap<>();
    Mockito.when(appCache.getCarChargingDetailsMap()).thenReturn(carChargingDetailsMap);
    // Act & Assert
    Assertions.assertThrows(
        CarChargingBusinessException.class,
        () -> carChargingService.retrieveSummaryOfChargingSession(),
        "No sessions found!");
  }

  @DisplayName("Method to prepare Input")
  public CarChargingDetailsDto prepareInput() {
    return CarChargingDetailsDto.builder()
        .id(UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18"))
        .stationId("ABC-12345")
        .status(StatusEnum.IN_PROGRESS)
        .build();
  }
}
