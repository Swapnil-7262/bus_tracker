package com.swapnil.bustracker.controller;

import com.swapnil.bustracker.dto.ApiResponse;
import com.swapnil.bustracker.model.Bus;
import com.swapnil.bustracker.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bus")
    public ResponseEntity<ApiResponse<Bus>> createBus(@RequestBody Bus bus) {
        Bus saved = busService.createBus(bus);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bus created", saved));
    }

    @GetMapping("/bus")
    public ResponseEntity<ApiResponse<List<Bus>>> getAllBus() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Buses fetched", busService.getAllBus())
        );
    }

    @PutMapping("/bus/{busId}/route/{routeId}")
    public ResponseEntity<ApiResponse<Bus>> assignRoute(
            @PathVariable Long busId,
            @PathVariable Long routeId) {

        Bus updated = busService.assignRoute(busId, routeId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route assigned", updated));
    }

//    @PutMapping("/bus/{busId}/driver/{driverId}")
//    public ResponseEntity<ApiResponse<Bus>> assignDriver(
//            @PathVariable Long busId,
//            @PathVariable Long driverId) {
//
//        Bus updated = busService.assignDriver(busId, driverId);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Driver assigned", updated));
//    }

    @PutMapping("/{busId}/assign")
    public ResponseEntity<ApiResponse<Bus>> assignFleet(
            @PathVariable Long busId,
            @RequestBody com.swapnil.bustracker.dto.AssignmentRequest request) {

        Bus updated = busService.assignFleet(busId, request.getDriverId(), request.getRouteId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Fleet assignment updated", updated));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    @PutMapping("/{busId}/status")
    public ResponseEntity<ApiResponse<Bus>> updateStatus(
            @PathVariable Long busId,
            @RequestBody java.util.Map<String, String> payload) {

        String status = payload.get("status");
        Bus updated = busService.updateBusStatus(busId, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bus status updated", updated));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<Bus>>> getAvailableBuses() {
        List<Bus> buses = busService.getAvailableBuses();
        return ResponseEntity.ok(new ApiResponse<>(true, "Available buses", buses));
    }

    @PutMapping("/{busId}/complete")
    public ResponseEntity<ApiResponse<Bus>> completeJourney(@PathVariable Long busId) {
        Bus updated = busService.completeJourney(busId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Journey completed", updated));
    }

    @GetMapping("/driver/{driverId}/active")
    public ResponseEntity<ApiResponse<Bus>> getActiveBus(@PathVariable Long driverId) {
        Bus bus = busService.getActiveBusByDriver(driverId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Active bus", bus));
    }

    @PutMapping("/driver/{driverId}/release")
    public ResponseEntity<ApiResponse<String>> releaseBusByDriver(@PathVariable Long driverId) {
        busService.releaseBusByDriver(driverId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bus released on logout", null));
    }

}
