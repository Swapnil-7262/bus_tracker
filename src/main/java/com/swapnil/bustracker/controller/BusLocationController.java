package com.swapnil.bustracker.controller;

import com.swapnil.bustracker.dto.ApiResponse;
import com.swapnil.bustracker.dto.BusLocationResponse;
import com.swapnil.bustracker.model.BusLocation;
import com.swapnil.bustracker.service.BusLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bus")
@RequiredArgsConstructor
public class BusLocationController {
    private final BusLocationService busLocationService;

    @PostMapping("/{busId}/location")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long busId,
            @RequestBody BusLocation req) {

        if (req.getLatitude() == null || req.getLongitude() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Invalid coordinates", null));
        }

        busLocationService.updateLocation(
                busId,
                req.getLatitude(),
                req.getLongitude(),
                System.currentTimeMillis()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Location updated", null)
        );
    }

    @GetMapping("/{busId}/location")
    public ResponseEntity<ApiResponse<BusLocationResponse>> getLocation(
            @PathVariable Long busId) {

        BusLocation location = busLocationService.getLatest(busId);

        if (location == null) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Location not found", null));
        }

        long now = System.currentTimeMillis();

        boolean isActive = (now - location.getTimestamp() <= 30000);

        BusLocationResponse response = new BusLocationResponse(
                location.getBusId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getTimestamp(),
                isActive
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Location fetched", response)
        );
    }

}
