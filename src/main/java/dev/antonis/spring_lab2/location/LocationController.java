package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.location.dto.LocationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping()
    public List<LocationDto> getAllPublicLocations() {
        return locationService.getAllPublicLocations();
    }

    @GetMapping("/{id}")
    public LocationDto getPublicLocationById(@PathVariable Integer id) {
        return locationService.getPublicLocationById(id);
    }

    @GetMapping("/categories/{categoryId}")
    public List<LocationDto> getAllPublicLocationsFromACategory(@PathVariable("categoryId") Integer categoryId) {
        return locationService.getPublicLocationInSpecificCategory(categoryId);
    }

}
