package dev.antonis.spring_lab2.location.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

    public record UpdateLocationDto(@NotBlank String name, @NotNull Integer category,
                                    @NotNull Boolean isPrivate, @NotNull String description,
                                    @FutureOrPresent Instant latestUpdate
    )
    {}

