package dev.antonis.spring_lab2.category.dto;

import dev.antonis.spring_lab2.entity.Category;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record CategoryDto(@NotBlank String name, @NotBlank String symbol, @NotBlank String description) implements Serializable {

    public static CategoryDto fromCategory(Category category) {
        return new CategoryDto(category.getName(), category.getSymbol(), category.getDescription());
    }
}
