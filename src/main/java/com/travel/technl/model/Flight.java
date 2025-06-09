package com.travel.technl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Entity representing a flight record.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    /** Primary key for flight record */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the airline operating the flight */
    private String airline;

    /** Supplier name providing the flight data */
    private String supplier;

    /** Total fare of the flight */
    private BigDecimal fare;

    /** Departure airport IATA code (3-letter) */
    @Column(length = 3)
    private String departureAirport;

    /** Destination airport IATA code (3-letter) */
    @Column(length = 3)
    private String destinationAirport;

    /** Departure time in UTC (ISO_DATE_TIME) */
    private ZonedDateTime departureTime;

    /** Arrival time in UTC (ISO_DATE_TIME) */
    private ZonedDateTime arrivalTime;
}
