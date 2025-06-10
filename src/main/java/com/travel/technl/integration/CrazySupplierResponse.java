package com.travel.technl.integration;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CrazySupplierResponse {
    private String carrier;
    private BigDecimal basePrice;
    private BigDecimal tax;
    private String departureAirportName;
    private String arrivalAirportName;
    private String outboundDateTime;
    private String inboundDateTime;
}

