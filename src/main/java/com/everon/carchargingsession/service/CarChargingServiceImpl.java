package com.everon.carchargingsession.service;

import com.everon.carchargingsession.dto.CarChargingDetailsDto;
import com.everon.carchargingsession.exception.CarChargingBusinessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarChargingServiceImpl implements CarChargingService {

    @Override public CarChargingDetailsDto submitNewChargingSession(String stationId)
        throws CarChargingBusinessException {
        return null;
    }

    @Override public CarChargingDetailsDto stopChargingSession(UUID id)
        throws CarChargingBusinessException {
        return null;
    }

    @Override public List<CarChargingDetailsDto> retrieveAllChargingSession()
        throws CarChargingBusinessException {
        return null;
    }

    @Override public Object retrieveSummaryOfChargingSession() throws CarChargingBusinessException {
        return null;
    }
}
