package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.location.dto.LocationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        return locationRepository.findByIdAndIsPrivateFalse(id)
                .map(location ->
                        new LocationDto(location.getName(), location.getCategory().getId(),
                                location.getUserId(), location.getIsPrivate(), location.getDescription(),
                                location.getCoordinate(), location.getCreatedAt(), location.getLatestUpdate()))
                .orElseThrow(() -> new NoSuchElementException("Public location not found with id: " + id));
    }

    public List<LocationDto> getPublicLocationInSpecificCategory(Integer categoryId) {
        return locationRepository.findByCategory_IdAndIsPrivateFalse(categoryId).stream().map(LocationDto::fromLocation).toList();
    }
}
