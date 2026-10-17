package com.swapnil.bustracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String routeName;

    private String color;        // ✅ add
    private String description;
//    @Column(nullable = false)
//    private String source;
//    @Column(nullable = false)
//    private String destination;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Stop> stops;
}
