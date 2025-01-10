package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.category.CategoryRepository;
import dev.antonis.spring_lab2.entity.Category;
import dev.antonis.spring_lab2.entity.Location;
import dev.antonis.spring_lab2.location.dto.LocationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Service
public class LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class);
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

    public Location addNewLocation(@Valid LocationDto locationDto) {
        if (locationRepository.existsByName(locationDto.name())) {
            throw new IllegalArgumentException("This location already exists:" + locationDto.name());
        }

        double lon = locationDto.coordinate().getPosition().getLon();
        double lat = locationDto.coordinate().getPosition().getLat();
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }

        var geo = Geometries.mkPoint(new G2D(lon, lat), WGS84);

        Category category = categoryRepository.findById(locationDto.category())
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + locationDto.category()));

        Location location = new Location();
        location.setName(locationDto.name());
        location.setCategory(category);
        location.setUserId(locationDto.userId());
        location.setIsPrivate(locationDto.isPrivate());
        location.setDescription(locationDto.description());
        location.setCoordinate(geo);
        location.setCreatedAt(locationDto.createdAt());
        location.setLatestUpdate(locationDto.latestUpdate());

        return locationRepository.save(location);
    }
}
