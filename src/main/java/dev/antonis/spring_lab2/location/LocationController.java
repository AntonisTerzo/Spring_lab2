package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.location.dto.LocationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/public-locations")
        public List<LocationDto> getAllPublicLocations() {
            return locationService.getAllPublicLocations();
        }

        @GetMapping("/public-locations/{id}")
        public LocationDto getPublicLocationById(@PathVariable Integer id) {
        return locationService.getPublicLocationById(id);
        }
    }
