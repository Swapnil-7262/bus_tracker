package com.swapnil.bustracker.service;

import com.swapnil.bustracker.model.Route;
import com.swapnil.bustracker.model.Stop;
import com.swapnil.bustracker.repository.RouteRepository;
import com.swapnil.bustracker.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService {

    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    public List<Stop> getStopsByRoute(Long routeId) {
        return stopRepository.findByRouteIdOrderByStopOrderAsc(routeId);
    }

    public Stop addStop(Long routeId, Stop stop) {

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        stop.setRoute(route);

        return stopRepository.save(stop);
    }

    public void deleteStop(Long stopId) {
        stopRepository.deleteById(stopId);
    }

    public Stop updateStopOrder(Long stopId, int newOrder) {

        Stop stop = stopRepository.findById(stopId)
                .orElseThrow(() -> new RuntimeException("Stop not found"));

        stop.setStopOrder(newOrder);

        return stopRepository.save(stop);
    }
}