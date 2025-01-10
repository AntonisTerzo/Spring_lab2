package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.entity.Location;
import dev.antonis.spring_lab2.location.dto.LocationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/public")
    public List<LocationDto> getAllPublicLocations() {
        return locationService.getAllPublicLocations();
    }

    @GetMapping("/{id}")
    public LocationDto getPublicLocationById(@PathVariable @NotNull Integer id) {
        return locationService.getPublicLocationById(id);
    }

    @GetMapping("/categories/{categoryId}")
    public List<LocationDto> getAllPublicLocationsFromACategory(@PathVariable @NotNull Integer categoryId) {
        return locationService.getPublicLocationInSpecificCategory(categoryId);
    }

    @GetMapping("/users/{userId}")
    public List<LocationDto> getLocationsByUserId(@PathVariable @NotNull Integer userId) {
        return locationService.getLocationsByUserId(userId);
    }

    @GetMapping("/area")
    public ResponseEntity<List<LocationDto>> getLocationsWithinRadius(
          @NotNull @RequestParam double lon,
          @NotNull @RequestParam double lat,
          @NotNull @RequestParam double radius
    ) {
        return ResponseEntity.ok(locationService.getLocationsWithinRadius(lon, lat, radius));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNewLocation(@RequestBody @Valid LocationDto locationDto) {
        Location location = locationService.addNewLocation(locationDto);
        return ResponseEntity.created(URI.create("/api/locations" + location)).build();
    }
}
