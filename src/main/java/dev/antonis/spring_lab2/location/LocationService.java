package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.category.CategoryRepository;
import dev.antonis.spring_lab2.entity.Category;
import dev.antonis.spring_lab2.entity.Location;
import dev.antonis.spring_lab2.location.dto.LocationDto;
import dev.antonis.spring_lab2.location.dto.UpdateLocationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    public LocationService(LocationRepository locationRepository, CategoryRepository categoryRepository) {
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<LocationDto> getAllPublicLocations() {
        return locationRepository.findByIsPrivateFalse().stream().map(LocationDto::fromLocation).toList();
    }

    public LocationDto getPublicLocationById(@NotNull Integer id) {
        return locationRepository.findByIdAndIsPrivateFalse(id)
                .map(location ->
                        new LocationDto(location.getName(), location.getCategory().getId(),
                                location.getUserId(), location.getIsPrivate(), location.getDescription(),
                                location.getCoordinates(), location.getCreatedAt(), location.getLatestUpdate()))
                .orElseThrow(() -> new NoSuchElementException("Public location not found with id: " + id));
    }

    public List<LocationDto> getPublicLocationInSpecificCategory(@NotNull Integer categoryId) {

        var findPublicCategory = locationRepository.findByCategory_IdAndIsPrivateFalse(categoryId);

        if (findPublicCategory.isEmpty()) {
            throw new NoSuchElementException("No public location found in category with id " + categoryId);
        }
        return findPublicCategory.stream().map(LocationDto::fromLocation).toList();
    }

    public List<LocationDto> getLocationsByUserId(@NotNull Integer userId) {
        var findUser = locationRepository.findByUserId(userId);

        if (findUser.isEmpty()) {
            throw new NoSuchElementException("No location found for the user with id " + userId);
        }
        return findUser.stream().map(LocationDto::fromLocation).toList();
    }

    public List<LocationDto> getLocationsWithinRadius(@NotNull Double lon, @NotNull Double lat, @NotNull Double radius) {
        // Construct WKT Point
        String point = String.format("POINT(%f %f)", lon, lat);

        return locationRepository.findWithinRadius(point, radius).stream().map(LocationDto::fromLocation).toList();
    }

    @Transactional
    public Location addNewLocation(@Valid LocationDto locationDto) {
        if (locationRepository.existsByName(locationDto.name())) {
            throw new IllegalArgumentException("This location already exists:" + locationDto.name());
        }

        geolocationCoordinates(locationDto.coordinates());

        Category category = categoryRepository.findById(locationDto.category())
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + locationDto.category()));

        Location location = new Location();
        location.setName(locationDto.name());
        location.setCategory(category);
        location.setUserId(locationDto.userId());
        location.setIsPrivate(locationDto.isPrivate());
        location.setDescription(locationDto.description());
        location.setCoordinates(locationDto.coordinates());
        location.setCreatedAt(locationDto.createdAt());
        location.setLatestUpdate(locationDto.latestUpdate());

        return locationRepository.save(location);
    }

    @Transactional
    public void updateLocation(Integer id, @Valid UpdateLocationDto updateLocationDto) {

        Location existingLocation = locationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No location exists with this id: " + id));

        Category category = categoryRepository.findById(updateLocationDto.category())
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + updateLocationDto.category()));

        if (updateLocationDto.name() != null) {
            existingLocation.setName(updateLocationDto.name());
        }
            existingLocation.setCategory(category);
        if (updateLocationDto.isPrivate() != null)
            existingLocation.setIsPrivate(updateLocationDto.isPrivate());
        if (updateLocationDto.description() != null) {
            existingLocation.setDescription(updateLocationDto.description());
        }
        if (updateLocationDto.latestUpdate() != null)
            existingLocation.setLatestUpdate(updateLocationDto.latestUpdate());
        locationRepository.save(existingLocation);
    }

    @Transactional
    public void deleteLocation(Integer id) {
        locationRepository.deleteById(id);
    }

    private void geolocationCoordinates(Point<G2D> coordinates) {
        double lon = coordinates.getPosition().getLon();
        double lat = coordinates.getPosition().getLat();
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }
    }
}

