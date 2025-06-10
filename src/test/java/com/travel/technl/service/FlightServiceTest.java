package com.travel.technl.service;


import com.travel.technl.exception.FlightNotFoundException;
import com.travel.technl.model.Flight;
import com.travel.technl.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepo;

    @Mock
    private CrazySupplierService crazySupplier;

    @InjectMocks
    private FlightService flightService;

    private Flight testFlight;

    @BeforeEach
    void setUp() {
        testFlight = new Flight();
        testFlight.setId(1L);
        testFlight.setAirline("MockAir");
        testFlight.setDepartureAirport("FRA");
        testFlight.setDestinationAirport("JFK");
    }

    @Test
    void testSaveFlight() {
        when(flightRepo.save(testFlight)).thenReturn(testFlight);

        Flight savedFlight = flightService.saveFlight(testFlight);

        assertNotNull(savedFlight);
        assertEquals("MockAir", savedFlight.getAirline());
        verify(flightRepo, times(1)).save(testFlight);
    }

    @Test
    void testUpdateFlight_Success() {
        when(flightRepo.findById(1L)).thenReturn(Optional.of(testFlight));
        when(flightRepo.save(any(Flight.class))).thenReturn(testFlight);

        Flight updatedFlight = flightService.updateFlight(1L, testFlight);

        assertNotNull(updatedFlight);
        assertEquals("MockAir", updatedFlight.getAirline());
        verify(flightRepo, times(1)).findById(1L);
        verify(flightRepo, times(1)).save(any(Flight.class));
    }

    @Test
    void testUpdateFlight_NotFound() {
        when(flightRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> flightService.updateFlight(1L, testFlight));
        verify(flightRepo, times(1)).findById(1L);
    }

    @Test
    void testDeleteFlight_Success() {
        when(flightRepo.existsById(1L)).thenReturn(true);
        doNothing().when(flightRepo).deleteById(1L);

        assertDoesNotThrow(() -> flightService.deleteFlight(1L));
        verify(flightRepo, times(1)).existsById(1L);
        verify(flightRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFlight_NotFound() {
        when(flightRepo.existsById(1L)).thenReturn(false);

        assertThrows(FlightNotFoundException.class, () -> flightService.deleteFlight(1L));
        verify(flightRepo, times(1)).existsById(1L);
    }

}

