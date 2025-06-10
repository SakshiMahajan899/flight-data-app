package com.travel.technl.exception;

/**
 * Custom exception for handling flight not found scenarios.
 */
public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String message) {
        super(message);
    }
}
