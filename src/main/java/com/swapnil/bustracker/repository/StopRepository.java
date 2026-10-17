package com.swapnil.bustracker.repository;

import com.swapnil.bustracker.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StopRepository extends JpaRepository<Stop, Long> {

    List<Stop> findByRouteIdOrderByStopOrderAsc(Long routeId);
}
