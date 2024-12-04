package dev.antonis.spring_lab2.category.dto;

import dev.antonis.spring_lab2.entity.Category;

public record CategoryDto(String name, String symbol, String description) {

    public static CategoryDto fromCategory(Category category) {
        return new CategoryDto(category.getName(), category.getSymbol(), category.getDescription());
    }
}
