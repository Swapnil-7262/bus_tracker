package com.swapnil.bustracker.service;

import com.swapnil.bustracker.model.BusLocation;
import com.swapnil.bustracker.repository.BusLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusLocationService {

    @Autowired
    private BusLocationRepository busLocationRepository;

    public void updateLocation(Long busId, double lat, double lng, long l) {

        BusLocation location = new BusLocation();
        location.setBusId(busId);
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setTimestamp(System.currentTimeMillis());

        busLocationRepository.save(location); // overwrite same row
    }

    public BusLocation getLatest(Long busId) {
        return busLocationRepository.findById(busId).orElse(null);
    }
}
