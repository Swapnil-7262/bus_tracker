package com.swapnil.bustracker.repository;

import com.swapnil.bustracker.model.Bus;
import com.swapnil.bustracker.model.BusStatus;
import com.swapnil.bustracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByStatus(BusStatus status);
    boolean existsByBusNumber(String busNumber);
    List<Bus> findByInUseFalseAndStatus(BusStatus status);
    boolean existsByDriver(User driver);
    Optional<Bus> findByDriverIdAndInUseTrue(Long driverId);

}
