package com.everon.carchargingsession.integrationTest;

import com.everon.carchargingsession.dto.AppCache;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarChargingSessionIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @DisplayName("Test method to submit a new charging session")
  @Test
  public void testSubmitNewChargingSession() throws CarChargingBusinessException, Exception {
    AppCache.getAppCache().clearCache();
    AppCache.getAppCache().getInProgressSessions().invalidateAll();
    AppCache.getAppCache().getStoppedSessions().invalidateAll();
    // Act
    mockMvc
        .perform(
            post("/chargingSessions").contentType(MediaType.APPLICATION_JSON).content("ABC-12345"))
        // Assert
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.stationId").value("ABC-12345"))
        .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    mockMvc
        .perform(get("/chargingSessions/summary"))
        // Assert
        .andExpect(jsonPath("$.totalCount").value(1))
        .andExpect(jsonPath("$.startedCount").value(1))
        .andExpect(jsonPath("$.stoppedCount").value(0))
        .andExpect(status().isOk());
    mockMvc
        .perform(get("/chargingSessions"))
        // Assert
        .andExpect(status().isOk());
  }
}
