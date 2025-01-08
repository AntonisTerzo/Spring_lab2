package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.location.dto.LocationDto;
import jakarta.validation.constraints.NotNull;
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

    public LocationDto getPublicLocationById(@NotNull Integer id) {
        return locationRepository.findByIdAndIsPrivateFalse(id)
                .map(location ->
                        new LocationDto(location.getName(), location.getCategory().getId(),
                                location.getUserId(), location.getIsPrivate(), location.getDescription(),
                                location.getCoordinate(), location.getCreatedAt(), location.getLatestUpdate()))
                .orElseThrow(() -> new NoSuchElementException("Public location not found with id: " + id));
    }

    public List<LocationDto> getPublicLocationInSpecificCategory(@NotNull Integer categoryId) {

        var locations = locationRepository.findByCategory_IdAndIsPrivateFalse(categoryId);

        if (locations.isEmpty()) {
            throw new NoSuchElementException("No public location found in category with id " + categoryId);
        }
        return locations.stream().map(LocationDto::fromLocation).toList();
    }

    public List<LocationDto> getLocationsByUserId(@NotNull Integer userId) {
         var locations = locationRepository.findByUserId(userId);

         if (locations.isEmpty()) {
             throw new NoSuchElementException("No location found for the user with id " + userId);
         }
         return locations.stream().map(LocationDto::fromLocation).toList();
    }

    public List<LocationDto> getLocationsWithinRadius(@NotNull Double lon, @NotNull Double lat, @NotNull Double radius) {
        // Construct WKT Point
        String point = String.format("POINT(%f %f)", lon, lat);

        return locationRepository.findWithinRadius(point, radius).stream().map(LocationDto::fromLocation).toList();
    }


}
