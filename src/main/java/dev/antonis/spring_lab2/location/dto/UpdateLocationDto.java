package dev.antonis.spring_lab2.location.dto;

import dev.antonis.spring_lab2.entity.Location;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

import java.time.Instant;

    public record UpdateLocationDto(@NotBlank String name, Integer category, Integer userId,
                                    @NotNull Boolean isPrivate, @NotNull String description, Point<G2D> coordinates, Instant createdAt,
                                    @FutureOrPresent Instant latestUpdate
    ) {
        public static UpdateLocationDto fromLocation(Location location) {
            Point<G2D> extractedCoordinates = Geometries.mkPoint(
                    new org.geolatte.geom.G2D(
                            location.getCoordinates().getPosition().getLat(),
                            location.getCoordinates().getPosition().getLon()
                    ),
                    CoordinateReferenceSystems.WGS84
            );
            return new UpdateLocationDto(location.getName(), location.getCategory().getId(), location.getUserId(),
                    location.getIsPrivate(), location.getDescription(),
                    extractedCoordinates, location.getCreatedAt(), location.getLatestUpdate());
        }
    }

