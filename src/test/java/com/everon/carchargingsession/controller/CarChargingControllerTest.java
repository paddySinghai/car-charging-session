package com.everon.carchargingsession.controller;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.CarChargingSummaryDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import com.everon.carchargingsession.service.CarChargingService;
import com.everon.carchargingsession.util.HelperUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Test class for CarChargingController */
@SpringBootTest
@AutoConfigureMockMvc
public class CarChargingControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CarChargingService carChargingService;

  @DisplayName("Test method to submit a new charging session")
  @Test
  public void testSubmitNewChargingSession() throws CarChargingBusinessException, Exception {
    // Arrange
    CarChargingDetailsDto carChargingDetailsDto =
        HelperUtil.prepareChargingSessionDetailsInput("ABC-12345");
    Gson gson = new Gson();

    given(carChargingService.submitNewChargingSession("ABC-12345"))
        .willReturn(carChargingDetailsDto);
    // Act
    mockMvc
        .perform(
            post("/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson("ABC-12345")))
        // Assert
        .andExpect(status().isCreated());
  }

  @DisplayName("Test method to stop charging Session")
  @Test
  public void testStopChargingSession() throws Exception {
    // Arrange
    CarChargingDetailsDto carChargingDetailsDto = prepareInput();
    carChargingDetailsDto.setStatus(StatusEnum.FINISHED);
    given(
            carChargingService.stopChargingSession(
                UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18")))
        .willReturn(carChargingDetailsDto);
    // Act
    mockMvc
        .perform(put("/chargingSessions/d9bb7458-d5d9-4de7-87f7-7f39edd51d18"))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stationId").value("ABC-12345"))
        .andExpect(jsonPath("$.id").value("d9bb7458-d5d9-4de7-87f7-7f39edd51d18"))
        .andExpect(jsonPath("$.status").value("FINISHED"));
  }

  @DisplayName("Test method to retrieve all charging sessions")
  @Test
  public void testRetrieveAllChargingSession() throws Exception {
    // Arrange
    CarChargingDetailsDto carChargingDetailsDto = prepareInput();
    List<CarChargingDetailsDto> carChargingDetailsDtoList = new ArrayList<>();
    carChargingDetailsDtoList.add(carChargingDetailsDto);
    given(carChargingService.retrieveAllChargingSession()).willReturn(carChargingDetailsDtoList);
    // Act
    mockMvc
        .perform(get("/chargingSessions"))
        // Assert
        .andExpect(status().isOk());
  }

  @DisplayName("Test method to retrieve summary of Charging Sessions")
  @Test
  public void testRetrieveSummaryChargingSession() throws Exception {
    CarChargingSummaryDto carChargingSummaryDto =
        CarChargingSummaryDto.builder().totalCount(5).startedCount(3).stoppedCount(2).build();
    given(carChargingService.retrieveSummaryOfChargingSession()).willReturn(carChargingSummaryDto);
    // Act
    mockMvc
        .perform(get("/chargingSessions/summary"))
        // Assert
        .andExpect(jsonPath("$.totalCount").value(5))
        .andExpect(jsonPath("$.startedCount").value(3))
        .andExpect(jsonPath("$.stoppedCount").value(2))
        .andExpect(status().isOk());
  }

  @DisplayName("Method to prepare Input")
  public CarChargingDetailsDto prepareInput() {
    return CarChargingDetailsDto.builder()
        .id(UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18"))
        .stationId("ABC-12345")
        .startedAt(LocalDateTime.parse("2019-05-06T19:00:20.529"))
        .stoppedAt(LocalDateTime.parse("2019-05-06T21:17:01.198"))
        .status(StatusEnum.IN_PROGRESS)
        .build();
  }
}
