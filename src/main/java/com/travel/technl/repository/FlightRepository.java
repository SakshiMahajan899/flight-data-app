package com.travel.technl.repository;

import com.travel.technl.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirportAndDestinationAirportAndAirlineContainingIgnoreCase(
            String departureAirport, String destinationAirport, String airline
    );
}
