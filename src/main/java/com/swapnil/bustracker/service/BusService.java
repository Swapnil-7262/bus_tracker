package com.swapnil.bustracker.service;

import com.swapnil.bustracker.model.Bus;
import com.swapnil.bustracker.model.BusStatus;
import com.swapnil.bustracker.model.Route;
import com.swapnil.bustracker.model.User;
import com.swapnil.bustracker.repository.BusRepository;
import com.swapnil.bustracker.repository.RouteRepository;

import com.swapnil.bustracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    public Bus createBus(Bus bus) {

        // Normalize bus number
        String normalizedBusNumber = bus.getBusNumber()
                .trim()
                .toUpperCase()
                .replaceAll("\\s+", "");

        if (busRepository.existsByBusNumber(normalizedBusNumber)) {
            throw new RuntimeException("Bus already exists");
        }

        bus.setBusNumber(normalizedBusNumber);

        // Normalize plate number
        if (bus.getPlateNumber() != null) {
            String normalizedPlate = bus.getPlateNumber()
                    .trim()
                    .toUpperCase()
                    .replaceAll("\\s+", "");  // 🔥 removes all spaces

            bus.setPlateNumber(normalizedPlate);
        }

        return busRepository.save(bus);
    }

    public List<Bus> getAllBus() {
        return busRepository.findAll();
    }

    public Bus assignRoute(Long busId, Long routeId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        bus.setRoute(route);
        return busRepository.save(bus);
    }

    public Bus assignDriver(Long busId, Long driverId) {

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (!driver.getRole().name().equals("DRIVER")) {
            throw new RuntimeException("User is not a driver");
        }

        bus.setDriver(driver);
        return busRepository.save(bus);
    }
    public Bus assignFleet(Long busId, Long driverId, Long routeId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        if (bus.isInUse()) {
            throw new RuntimeException("Bus already in use");
        }


        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        boolean alreadyAssigned = busRepository.existsByDriver(driver);

        if (alreadyAssigned) {
            throw new RuntimeException("Driver already has a bus");
        }

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        if (!driver.getRole().name().equals("DRIVER")) {
            throw new RuntimeException("User is not a driver");
        }

        // 🔥 IMPORTANT PART
        bus.setDriver(driver);
        bus.setRoute(route);
        bus.setInUse(true);   // LOCK bus

        return busRepository.save(bus);
    }

    public Bus updateBusStatus(Long busId, String status) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // If you use an Enum, use: bus.setStatus(BusStatus.valueOf(status.toUpperCase()));
        // If you use a String, just use:
        bus.setStatus(BusStatus.valueOf(status.toUpperCase()));

        return busRepository.save(bus);
    }

    public List<Bus> getAvailableBuses() {
        return busRepository.findByInUseFalseAndStatus(BusStatus.ACTIVE);
    }

    public Bus completeJourney(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // 🔥 RELEASE
        bus.setDriver(null);
        bus.setInUse(false);

        return busRepository.save(bus);
    }

    public Bus getActiveBusByDriver(Long driverId) {
        return busRepository.findByDriverIdAndInUseTrue(driverId)
                .orElse(null);
    }

    public void releaseBusByDriver(Long driverId) {

        Bus bus = busRepository.findByDriverIdAndInUseTrue(driverId)
                .orElse(null);

        if (bus != null) {
            bus.setDriver(null);
            bus.setInUse(false);
            busRepository.save(bus);
        }
    }
}
