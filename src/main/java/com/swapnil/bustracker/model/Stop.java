package com.swapnil.bustracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stopName;

    private double latitude;
    private double longitude;

    private Integer stopOrder; // order of stops
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
}
