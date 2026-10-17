package com.swapnil.bustracker.service;

import com.swapnil.bustracker.model.Route;
import com.swapnil.bustracker.model.Stop;
import com.swapnil.bustracker.repository.RouteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;

    public Route createRoute(Route route) {

        if (route.getRouteName() == null || route.getRouteName().trim().isEmpty()) {
            throw new RuntimeException("Route name is required");
        }

        String routeName = route.getRouteName().trim().toUpperCase();
        route.setRouteName(routeName);

        if (routeRepository.existsByRouteNameIgnoreCase(routeName)) {
            throw new RuntimeException("Route already exists");
        }

        if (route.getStops() == null || route.getStops().isEmpty()) {
            throw new RuntimeException("At least one stop is required");
        }

        List<Stop> validStops = new ArrayList<>();
        int order = 1;

        for (Stop stop : route.getStops()) {

            if (stop.getStopName() == null || stop.getStopName().trim().isEmpty()) {
                continue;
            }

            if (stop.getLatitude() < -90 || stop.getLatitude() > 90) {
                throw new RuntimeException("Invalid latitude");
            }

            if (stop.getLongitude() < -180 || stop.getLongitude() > 180) {
                throw new RuntimeException("Invalid longitude");
            }

            stop.setStopName(stop.getStopName().trim());
            stop.setRoute(route);
            stop.setStopOrder(order++);

            validStops.add(stop);
        }

        route.setStops(validStops);

        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes(){
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id ){
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

    public void deleteRouteById(Long id ){
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Route with ID " + id + " not found");
        }
        routeRepository.deleteById(id);

    }

}
