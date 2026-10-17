package com.swapnil.bustracker.repository;

import com.swapnil.bustracker.model.BusLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusLocationRepository extends JpaRepository<BusLocation, Long> {
}
