package com.travel.technl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrazySupplierRequest {
    private String from;
    private String to;
    private String outboundDate;
    private String inboundDate;
}
