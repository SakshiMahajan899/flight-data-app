package com.travel.technl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.technl.dto.CrazySupplierRequest;
import com.travel.technl.model.CrazySupplier;
import com.travel.technl.model.Flight;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrazySupplierService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Retryable(
            value = { RestClientException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public List<Flight> search(String from, String to, String outboundDate, String inboundDate) {
        try {
            System.out.println("from->"+from+"to->"+to);
            CrazySupplierRequest request = new CrazySupplierRequest(from, to, outboundDate, inboundDate);
            ResponseEntity<CrazySupplier[]> response = restTemplate.postForEntity(
                    "http://localhost:8080/mock-crazy-supplier/flights", request, CrazySupplier[].class
            );

            return Arrays.stream(response.getBody())
                    .map(this::convertToFlight)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Failed to fetch CrazySupplier data", e);
            return Collections.emptyList();
        }
    }

    private Flight convertToFlight(CrazySupplier res) {
        System.out.println("----"+res);
        return Flight.builder()
                .airline(res.getCarrier())
                .supplier("CrazySupplier")
                .fare(BigDecimal.valueOf(res.getBasePrice() + res.getTax()))
                .departureAirport(res.getDepartureAirportName())
                .destinationAirport(res.getArrivalAirportName())
                .departureTime(convertCETToUTC(res.getOutboundDateTime()))
                .arrivalTime(convertCETToUTC(res.getInboundDateTime()))
                .build();
    }

    private ZonedDateTime convertCETToUTC(String dateTime) {

        System.out.println("----"+dateTime);
        return LocalDate.parse(dateTime)
                .atStartOfDay(ZoneId.of("CET"))
                .withZoneSameInstant(ZoneOffset.UTC);
    }
}
