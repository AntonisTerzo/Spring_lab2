package dev.antonis.spring_lab2.category;

import dev.antonis.spring_lab2.category.dto.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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


    @GetMapping("/categories/{id}")
    public CategoryDto getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/categories")
    public ResponseEntity<Void> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        int id = categoryService.addCategory(categoryDto);
        return ResponseEntity.created(URI.create("/categories" + id)).build();
    }
}
