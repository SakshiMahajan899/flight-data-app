package com.travel.technl.controller;


import com.travel.technl.dto.CrazySupplierFlight;
import com.travel.technl.dto.CrazySupplierRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mock-crazy-supplier")
public class CrazySupplierMockController {

    @PostMapping("/flights")
    public ResponseEntity<List<CrazySupplierFlight>> getFlights(@RequestBody CrazySupplierRequest request) {
        CrazySupplierFlight flight1 = new CrazySupplierFlight(
                "Lufthansa",
                220.0,
                50.0,
                request.getFrom(),
                request.getTo(),
                request.getOutboundDate(),
                request.getInboundDate()
        );

        return ResponseEntity.ok(List.of(flight1));
    }
}
