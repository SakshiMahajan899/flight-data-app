package com.travel.technl.integration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class CrazySupplierClient {

    private final WebClient webClient;

    public CrazySupplierClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.crazy-supplier.com").build();
    }

    public List<CrazySupplierResponse> getFlights(String from, String to, LocalDate outboundDate, LocalDate inboundDate) {
        return webClient.post()
                .uri("/flights")
                .bodyValue(Map.of(
                        "from", from,
                        "to", to,
                        "outboundDate", outboundDate.toString(),
                        "inboundDate", inboundDate.toString()
                ))
                .retrieve()
                .bodyToFlux(CrazySupplierResponse.class)
                .collectList()
                .block();
    }
}

