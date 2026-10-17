package com.swapnil.bustracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusLocationResponse {
    private Long busId;
    private double latitude;
    private double longitude;
    private long timestamp;
    private boolean active;
}
