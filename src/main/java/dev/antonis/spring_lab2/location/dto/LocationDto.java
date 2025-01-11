package dev.antonis.spring_lab2.location.dto;

import dev.antonis.spring_lab2.entity.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

import java.time.Instant;

public record LocationDto(@NotBlank String name, @NotNull Integer category, @NotNull Integer userId,
                          @NotNull Boolean isPrivate, String description, @NotNull Point<G2D> coordinates, @NotNull Instant createdAt,
                          Instant latestUpdate
) {
    public static LocationDto fromLocation(Location location) {
        Point<G2D> extractedCoordinates = Geometries.mkPoint(
                new org.geolatte.geom.G2D(
                        location.getCoordinates().getPosition().getLat(),
                        location.getCoordinates().getPosition().getLon()
                ),
                CoordinateReferenceSystems.WGS84
        );
        return new LocationDto(location.getName(), location.getCategory().getId(), location.getUserId(),
                location.getIsPrivate(), location.getDescription(),
                extractedCoordinates, location.getCreatedAt(), location.getLatestUpdate());
    }
}

/*
Geometries.mkPoint:
Creates a Point<G2D> using the Geometries utility class provided by Geolatte.
CoordinateReferenceSystems.WGS84:
Specifies the spatial reference system (SRID 4326 for GPS).
*/