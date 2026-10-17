package com.swapnil.bustracker.controller;

import com.swapnil.bustracker.dto.ApiResponse;
import com.swapnil.bustracker.model.Route;
import com.swapnil.bustracker.model.Stop;
import com.swapnil.bustracker.service.BusService;
import com.swapnil.bustracker.service.RouteService;
import com.swapnil.bustracker.service.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final BusService busService;
    private final RouteService routeService;
    private final StopService stopService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Route>> createRoute(@RequestBody Route route) {
        Route saved = routeService.createRoute(route);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route created", saved));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Route>>> getAllRoutes() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Routes fetched", routeService.getAllRoutes())
        );
    }
    @GetMapping("/routes/{id}")
    public ResponseEntity<ApiResponse<Route>> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Route fetched", routeService.getRouteById(id))
        );
    }

    @DeleteMapping("/routes/{id}")
    public  ResponseEntity<ApiResponse<Route>> deleteRoute(@PathVariable Long id) {

        routeService.deleteRouteById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Route fetched", null)
        );
    }


    @GetMapping("/routes/{routeId}/stops")
    public ResponseEntity<ApiResponse<List<Stop>>> getStops(@PathVariable Long routeId) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Stops fetched", stopService.getStopsByRoute(routeId))
        );
    }

    @PostMapping("/routes/{routeId}/stops")
    public ResponseEntity<ApiResponse<Stop>> addStop(
            @PathVariable Long routeId,
            @RequestBody Stop stop) {

        Stop saved = stopService.addStop(routeId, stop);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stop added", saved));
    }

    @DeleteMapping("/stops/{stopId}")
    public ResponseEntity<ApiResponse<String>> deleteStop(@PathVariable Long stopId) {
        stopService.deleteStop(stopId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stop deleted", null));
    }

    @PutMapping("/stops/{stopId}/order")
    public ResponseEntity<ApiResponse<Stop>> updateStopOrder(
            @PathVariable Long stopId,
            @RequestParam int order) {

        Stop updated = stopService.updateStopOrder(stopId, order);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stop order updated", updated));
    }
}
