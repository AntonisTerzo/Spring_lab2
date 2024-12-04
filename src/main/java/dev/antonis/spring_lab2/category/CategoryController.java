package dev.antonis.spring_lab2.category;

import dev.antonis.spring_lab2.category.dto.CategoryDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
