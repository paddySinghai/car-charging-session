package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.AppCache;
import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.dto.StatusEnum;
import com.everon.carchargingsession.util.HelperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarSessionSummaryRepositoryTest {

  private CarSessionSummaryRepositoryImpl carSessionSummaryRepository;

  @BeforeEach
  public void init() {
    carSessionSummaryRepository = new CarSessionSummaryRepositoryImpl();
    AppCache.getAppCache().getInProgressSessions().invalidateAll();
    AppCache.getAppCache().getStoppedSessions().invalidateAll();
  }

  @DisplayName("Test method to Update ongoing sessions in the Cache")
  @Test
  public void testUpdateInProgressSessions() {
    // Arrange

    CarChargingDetailsDto expected = HelperUtil.prepareChargingSessionDetailsInput("abc_123");
    // Act
    carSessionSummaryRepository.updateInProgressSessions(expected);
    // Assert
    Assertions.assertNotNull(expected);
    Assertions.assertTrue(AppCache.getAppCache().getInProgressSessions().asMap().containsKey(expected.getId()));
  }

  @DisplayName("Test method to Update stopped/terminated sessions in the Cache")
  @Test
  public void testUpdateStoppedSessions() {
    // Arrange

    CarChargingDetailsDto expected = HelperUtil.prepareChargingSessionDetailsInput("abc_123");
    expected.setStatus(StatusEnum.FINISHED);
    // Act
    carSessionSummaryRepository.updateStoppedSessions(expected);
    // Assert
    Assertions.assertNotNull(expected);
    Assertions.assertTrue(AppCache.getAppCache().getStoppedSessions().asMap().containsKey(expected.getId()));
  }

  @DisplayName("Test method to fetch the count of ongoing sessions in the past one minute")
  @Test
  public void testGetInProgressSessionCount() {
    // Arrange

    CarChargingDetailsDto obj1 = HelperUtil.prepareChargingSessionDetailsInput("abc_123");
    carSessionSummaryRepository.updateInProgressSessions(obj1);
    CarChargingDetailsDto obj2 = HelperUtil.prepareChargingSessionDetailsInput("abc_1234");
    carSessionSummaryRepository.updateInProgressSessions(obj2);
    // Act
    long count = carSessionSummaryRepository.getInProgressSessionCount();
    // Assert
    Assertions.assertEquals(count, 2);
  }

  @DisplayName(
      "Test method to fetch the count of stopped/terminated sessions in the past one minute")
  @Test
  public void testGetStoppedSessionCount() {
    // Arrange

    CarChargingDetailsDto obj1 = HelperUtil.prepareChargingSessionDetailsInput("abc_123");
    carSessionSummaryRepository.updateStoppedSessions(obj1);
    // Act
    long count = carSessionSummaryRepository.getStoppedSessionCount();
    // Assert
    Assertions.assertEquals(1, count);
  }
}
