package com.swapnil.bustracker.repository;

import com.swapnil.bustracker.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
    boolean existsByRouteNameIgnoreCase(String routeName);
}
