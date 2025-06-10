package com.travel.technl.controller;

import com.travel.technl.model.Flight;
import com.travel.technl.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing flight operations.
 */
@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Slf4j
public class FlightController {

    private final FlightService flightService;

    /**
     * Creates a new flight.
     *
     * @param flight Flight details in request body
     * @return ResponseEntity containing the created Flight object
     */
    @PreAuthorize("hasAuthority('SCOPE_flight:write')")
    @PostMapping
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody Flight flight) {
        log.info("Creating flight: {}", flight);
        Flight savedFlight = flightService.saveFlight(flight);
        log.info("Flight created successfully: {}", savedFlight);
        return ResponseEntity.ok(savedFlight);
    }

    /**
     * Updates an existing flight.
     *
     * @param id Flight ID
     * @param flight Updated flight details
     * @return ResponseEntity containing the updated Flight object
     */
    @PreAuthorize("hasAuthority('SCOPE_flight:write')")
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody Flight flight) {
        log.info("Updating flight with ID {}: {}", id, flight);
        Flight updatedFlight = flightService.updateFlight(id, flight);
        log.info("Flight updated successfully: {}", updatedFlight);
        return ResponseEntity.ok(updatedFlight);
    }

    /**
     * Deletes a flight by ID.
     *
     * @param id Flight ID
     * @return ResponseEntity with no content upon successful deletion
     */
    @PreAuthorize("hasAuthority('SCOPE_flight:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        log.info("Deleting flight with ID {}", id);
        flightService.deleteFlight(id);
        log.info("Flight deletion successful for ID {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches for flights based on provided criteria.
     *
     * @param from Departure location
     * @param to Destination location
     * @param airline Optional airline filter
     * @param departureTime Optional departure time filter
     * @param arrivalTime Optional arrival time filter
     * @return ResponseEntity containing a list of matching flights
     */
    @PreAuthorize("hasAuthority('SCOPE_flight:read')")
    @GetMapping
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) String airline,
            @RequestParam(required = false) String departureTime,
            @RequestParam(required = false) String arrivalTime
    ) {
        log.info("Searching flights from {} to {} with filters: airline={}, departureTime={}, arrivalTime={}",
                from, to, airline, departureTime, arrivalTime);

        List<Flight> flights = flightService.searchFlights(from, to, airline, departureTime, arrivalTime);
        log.info("Found {} flights matching search criteria", flights.size());

        return ResponseEntity.ok(flights);
    }
}
