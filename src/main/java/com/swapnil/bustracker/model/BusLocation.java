package com.swapnil.bustracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusLocation {
    @Id
    private Long busId; // IMPORTANT: no auto increment

    private Double latitude;
    private Double longitude;
    private Long timestamp;
}
