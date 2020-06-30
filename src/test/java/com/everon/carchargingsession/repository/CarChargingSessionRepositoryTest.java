package com.everon.carchargingsession.repository;

import com.everon.carchargingsession.dto.AppCache;
import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.util.HelperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Test class for CarChargingSessionRepositoryImpl containing methods to save and search objects in
 * the cache
 */
@ExtendWith(MockitoExtension.class)
public class CarChargingSessionRepositoryTest {
  private CarChargingSessionRepositoryImpl carChargingSessionRepository;

  @BeforeEach
  public void init() {
    carChargingSessionRepository = new CarChargingSessionRepositoryImpl();
    AppCache.getAppCache().clearCache();
  }

  @DisplayName("Test method to add session in Cache")
  @Test
  public void testSave() {
    // Arrange
    String stationId = "abcd_12345";
    CarChargingDetailsDto expected = HelperUtil.prepareChargingSessionDetailsInput(stationId);
    // Act
    CarChargingDetailsDto actual = carChargingSessionRepository.save(expected);
    // Assert
    Assertions.assertEquals(actual, expected);
  }

  @DisplayName("Test method to retrieve session details by Id")
  @Test
  public void testFindById() {
    // Arrange
    String stationId = "abcd_12345";
    CarChargingDetailsDto expected = HelperUtil.prepareChargingSessionDetailsInput(stationId);
    carChargingSessionRepository.save(expected);
    // Act
    Optional<CarChargingDetailsDto> actual =
        carChargingSessionRepository.findById(expected.getId());
    // Assert
    Assertions.assertEquals(actual.get(), expected);
  }

  @DisplayName("Test method to retrieve session details by Id when session does not exists")
  @Test
  public void testFindByIdWhenSessionDoesNotExist() {
    // Arrange
    String stationId = "abcd_12345";
    CarChargingDetailsDto expected = HelperUtil.prepareChargingSessionDetailsInput(stationId);
    // Act
    Optional<CarChargingDetailsDto> actual =
        carChargingSessionRepository.findById(expected.getId());
    // Assert
    Assertions.assertTrue(!actual.isPresent());
  }

  @DisplayName("Test method to retrieve all the sessions")
  @Test
  public void testFindAll() {
    // Arrange
    String stationId = "abcd_12345";
    CarChargingDetailsDto expected = HelperUtil.prepareChargingSessionDetailsInput(stationId);

    List<CarChargingDetailsDto> carChargingList = new ArrayList<>();
    carChargingList.add(expected);

    carChargingSessionRepository.save(expected);
    // Act
    List<CarChargingDetailsDto> actual = carChargingSessionRepository.findAll();
    // Assert
    Assertions.assertNotNull(actual);
    Assertions.assertTrue(actual.get(0) == carChargingList.get(0));
  }
}
