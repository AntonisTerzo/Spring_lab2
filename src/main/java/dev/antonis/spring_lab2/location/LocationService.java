package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.location.dto.LocationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDto> getAllPublicLocations() {
        return locationRepository.findByIsPrivateFalse().stream().map(LocationDto::fromLocation).toList();
    }

    public LocationDto getPublicLocationById(Integer id) {
        return null;
    }
}
