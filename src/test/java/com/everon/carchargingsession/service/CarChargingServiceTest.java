package com.everon.carchargingsession.service;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.CarChargingSummaryDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import com.everon.carchargingsession.repository.CarChargingSessionRepository;
import com.everon.carchargingsession.repository.CarSessionSummaryRepository;
import com.everon.carchargingsession.util.HelperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

/** Test class for CarChargingService */
@ExtendWith(MockitoExtension.class)
public class CarChargingServiceTest {

  @InjectMocks CarChargingServiceImpl carChargingService;

  @Mock CarChargingSessionRepository carChargingSessionRepository;

  @Mock CarSessionSummaryRepository carSessionSummaryRepository;

  @DisplayName("Test method to submit a new charging session")
  // @Test
  public void testSubmitNewChargingSession() {
    // Arrange

    CarChargingDetailsDto actualValue = HelperUtil.prepareChargingSessionDetailsInput("abc-123");

    Mockito.when(carSessionSummaryRepository.updateInProgressSessions(actualValue))
        .thenReturn(actualValue);

    Mockito.when(carChargingSessionRepository.save(actualValue))
        .thenReturn(any(CarChargingDetailsDto.class));

    // Act
    CarChargingDetailsDto outputDto = carChargingService.submitNewChargingSession("abc-123");
    // Assert
    Assertions.assertEquals(actualValue.getStationId(), outputDto.getStationId());
    Assertions.assertEquals(actualValue.getStatus(), outputDto.getStatus());
    Assertions.assertNotNull(outputDto);
  }

  @DisplayName("Test method to stop charging Session")
  @Test
  public void testSopChargingSession() throws CarChargingBusinessException {
    // Arrange
    CarChargingDetailsDto actualValue = HelperUtil.prepareChargingSessionDetailsInput("abc-123");

    UUID inputUUID = UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18");

    Mockito.when(carChargingSessionRepository.findById(inputUUID))
        .thenReturn(Optional.of(actualValue));
    Mockito.when(carSessionSummaryRepository.updateStoppedSessions(actualValue))
        .thenReturn(actualValue);
    Mockito.when(carChargingSessionRepository.save(actualValue)).thenReturn(actualValue);

    // Act
    CarChargingDetailsDto outputDto = carChargingService.stopChargingSession(inputUUID);
    // Assert
    Assertions.assertEquals(actualValue.getStationId(), outputDto.getStationId());
    Assertions.assertEquals(StatusEnum.FINISHED, outputDto.getStatus());
    Assertions.assertEquals(actualValue.getId(), outputDto.getId());
    Assertions.assertNotNull(outputDto);
  }

  @DisplayName("Test method to stop charging Session with Status Finished")
  @Test
  public void testSopChargingSessionWithStatusFinished() {
    // Arrange
    CarChargingDetailsDto actualValue = HelperUtil.prepareChargingSessionDetailsInput("abc-123");
    actualValue.setStatus(StatusEnum.FINISHED);

    Mockito.when(carChargingSessionRepository.findById(actualValue.getId()))
        .thenReturn(Optional.of(actualValue));

    // Act & Assert
    Assertions.assertThrows(
        CarChargingBusinessException.class,
        () -> carChargingService.stopChargingSession(actualValue.getId()),
        "Session already Terminated for id:" + actualValue.getId());
  }

  @DisplayName("Test method to stop charging Session with Wrong Id")
  @Test
  public void testSopChargingSessionWithWrongId() {
    // Arrange
    CarChargingDetailsDto actualValue = HelperUtil.prepareChargingSessionDetailsInput("abc-123");
    actualValue.setStatus(StatusEnum.FINISHED);
    UUID inputUUID = UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d56");
    // Act & Assert
    Assertions.assertThrows(
        CarChargingBusinessException.class,
        () -> carChargingService.stopChargingSession(inputUUID),
        "No Active session found for id: " + inputUUID);
  }

  @DisplayName("Test Method to Retrieve all charging sessions")
  @Test
  public void retrieveAllChargingSession() {
    // Arrange
    CarChargingDetailsDto actualValue = HelperUtil.prepareChargingSessionDetailsInput("abc-123");

    List<CarChargingDetailsDto> carChargingSessionList = new ArrayList<>();
    carChargingSessionList.add(actualValue);

    Mockito.when(carChargingSessionRepository.findAll()).thenReturn(carChargingSessionList);
    // Act
    Collection<CarChargingDetailsDto> outputList = carChargingService.retrieveAllChargingSession();
    // Assert
    Assertions.assertNotNull(outputList);
  }

  @DisplayName("Test Method to Retrieve all charging sessions with No Submitted Sessions")
  @Test
  public void retrieveAllChargingSessionWithNoSubmittedSessions() {
    // Arrange
    List<CarChargingDetailsDto> carChargingSessionList = new ArrayList<>();
    Mockito.when(carChargingSessionRepository.findAll()).thenReturn(carChargingSessionList);
    // Act
    Collection<CarChargingDetailsDto> outputList = carChargingService.retrieveAllChargingSession();
    // Assert
    Assertions.assertTrue(outputList.isEmpty());
  }

  @DisplayName("Test method to retrieve summary of Charging Sessions")
  @Test
  public void testRetrieveSummaryChargingSession() {
    // Arrange
    CarChargingSummaryDto carChargingSummaryDto =
        CarChargingSummaryDto.builder().totalCount(3).startedCount(1).stoppedCount(2).build();

    Mockito.when(carSessionSummaryRepository.getInProgressSessionCount()).thenReturn(1L);
    Mockito.when(carSessionSummaryRepository.getStoppedSessionCount()).thenReturn(2L);

    // Act
    CarChargingSummaryDto outputDto = carChargingService.retrieveSummaryOfChargingSession();

    // Assert
    Assertions.assertEquals(carChargingSummaryDto.getTotalCount(), outputDto.getTotalCount());
    Assertions.assertEquals(carChargingSummaryDto.getStartedCount(), outputDto.getStartedCount());
    Assertions.assertEquals(carChargingSummaryDto.getStoppedCount(), outputDto.getStoppedCount());
  }
}
