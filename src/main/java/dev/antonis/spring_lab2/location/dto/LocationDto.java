package dev.antonis.spring_lab2.location.dto;

import dev.antonis.spring_lab2.entity.Location;

import java.time.Instant;

public record LocationDto(String name, Integer category, Integer userId,
                          Boolean isPrivate, String description, double[] coordinate, Instant createdAt, Instant latestUpdate
                         ) {
    public static LocationDto fromLocation(Location location) {
        double[] extractedCoordinates = new double[] {
                location.getCoordinate().getPosition().getLon(),
                location.getCoordinate().getPosition().getLat()
        };
        return new LocationDto(location.getName(), location.getCategory().getId(), location.getUserId(),
                location.getIsPrivate(), location.getDescription(),
                extractedCoordinates, location.getCreatedAt(), location.getLatestUpdate());
    }
}
