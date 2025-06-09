package com.travel.technl.service;

import com.travel.technl.model.Flight;
import com.travel.technl.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightRepository flightRepo;
    private final CrazySupplierService crazySupplier;

    public Flight saveFlight(Flight flight) {
        return flightRepo.save(flight);
    }

    public Flight updateFlight(Long id, Flight flight) {
        Flight existing = flightRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        BeanUtils.copyProperties(flight, existing, "id");
        return flightRepo.save(existing);
    }

    public void deleteFlight(Long id) {
        flightRepo.deleteById(id);
    }

    public List<Flight> searchFlights(String from, String to, String airline, String depTime, String arrTime) {
        List<Flight> localFlights = flightRepo.findByDepartureAirportAndDestinationAirportAndAirlineContainingIgnoreCase(
                from, to, airline == null ? "" : airline);

        List<Flight> crazyFlights = crazySupplier.search(from, to, depTime, arrTime);
        localFlights.addAll(crazyFlights);
        return localFlights;
    }
}
