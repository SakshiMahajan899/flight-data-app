package com.travel.technl.repository;

import com.travel.technl.model.Flight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepo;

    @Test
    void testFindByDepartureAndDestinationAndAirline() {
        // Arrange: Save test flights
        Flight flight1 = new Flight();
        flight1.setDepartureAirport("FRA");
        flight1.setDestinationAirport("JFK");
        flight1.setAirline("MockAir");
        flightRepo.save(flight1);

        Flight flight2 = new Flight();
        flight2.setDepartureAirport("FRA");
        flight2.setDestinationAirport("JFK");
        flight2.setAirline("AnotherAir");
        flightRepo.save(flight2);

        // Act: Search for flights
        List<Flight> results = flightRepo.findByDepartureAirportAndDestinationAirportAndAirlineContainingIgnoreCase("FRA", "JFK", "MockAir");

        // Assert: Validate results
        assertEquals(1, results.size());
        assertEquals("MockAir", results.get(0).getAirline());
    }

    @Test
    void testFindByDepartureAndDestination_NoResults() {
        List<Flight> results = flightRepo.findByDepartureAirportAndDestinationAirportAndAirlineContainingIgnoreCase("LHR", "LAX", "MockAir");

        assertTrue(results.isEmpty());
    }
}

