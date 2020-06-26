package com.everon.carchargingsession.controller;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import com.everon.carchargingsession.service.CarChargingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Test class for CarChargingController */
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CarChargingController.class})
@WebMvcTest
public class CarChargingControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CarChargingService carChargingService;

  /** Test method to submit a new charging session */
  @Test
  public void testSubmitNewChargingSession() throws CarChargingBusinessException, Exception {
    // Arrange
    String stationId = "ABC-12345";
    CarChargingDetailsDto carChargingDetailsDto =
        CarChargingDetailsDto
            .builder()
            .id(UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18"))
            .stationId("ABC-12345")
            .startedAt(LocalDateTime.parse("2019-05-06T19:00:20.529"))
            .status(StatusEnum.IN_PROGRESS)
            .build();

    given(carChargingService.submitNewChargingSession(stationId)).willReturn(carChargingDetailsDto);
    // Act
    mockMvc
        .perform(post("/chargingSessions"))
     //Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stationId").value("ABC-12345"))
        .andExpect(jsonPath("$.status").value(StatusEnum.IN_PROGRESS));
  }

  /** Test method to stop charging Session */
  public void testStopChargingSession() {}

  /** Test method to retrieve all charging sessions */
  public void testRetrieveAllChargingSession() {}

  /** Test method to retrieve summary of Charging Sessions */
  public void testRetrieveSummaryChargingSession() {}
}
