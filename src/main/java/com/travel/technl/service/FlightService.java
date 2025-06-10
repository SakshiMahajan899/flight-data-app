package com.travel.technl.service;

import com.travel.technl.exception.FlightNotFoundException;
import com.travel.technl.model.Flight;
import com.travel.technl.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for flight management operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightRepository flightRepo;
    private final CrazySupplierService crazySupplier;

    /**
     * Saves a new flight to the database.
     *
     * @param flight Flight object
     * @return Saved Flight entity
     */
    public Flight saveFlight(Flight flight) {
        log.info("Saving flight: {}", flight);
        return flightRepo.save(flight);
    }

    /**
     * Updates an existing flight.
     *
     * @param id Flight ID
     * @param flight Updated flight details
     * @return Updated Flight entity
     * @throws FlightNotFoundExceptio If flight not found
     */
    public Flight updateFlight(Long id, Flight flight) {
        Flight existing = flightRepo.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with ID: " + id));

        log.info("Updating flight ID {}: {}", id, flight);
        BeanUtils.copyProperties(flight, existing, "id");
        return flightRepo.save(existing);
    }

    /**
     * Deletes a flight by ID.
     *
     * @param id Flight ID
     * @throws FlightNotFoundException If flight not found
     */
    public void deleteFlight(Long id) {
        if (!flightRepo.existsById(id)) {
            throw new FlightNotFoundException("Flight not found with ID: " + id);
        }
        log.info("Deleting flight with ID {}", id);
        flightRepo.deleteById(id);
    }

    /**
     * Searches for flights based on criteria.
     *
     * @param from Departure location
     * @param to Destination location
     * @param airline Optional airline filter
     * @param depTime Optional departure time filter
     * @param arrTime Optional arrival time filter
     * @return List of flights matching criteria
     */
    public List<Flight> searchFlights(String from, String to, String airline, String depTime, String arrTime) {
        log.info("Searching flights from {} to {} with filters: airline={}, depTime={}, arrTime={}",
                from, to, airline, depTime, arrTime);

        List<Flight> localFlights = flightRepo.findByDepartureAirportAndDestinationAirportAndAirlineContainingIgnoreCase(
                from, to, airline == null ? "" : airline);

        List<Flight> crazyFlights = crazySupplier.search(from, to, depTime, arrTime);
        localFlights.addAll(crazyFlights);

        return localFlights;
    }
}
