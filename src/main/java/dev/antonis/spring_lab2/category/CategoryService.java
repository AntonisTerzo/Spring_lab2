package dev.antonis.spring_lab2.category;

import dev.antonis.spring_lab2.category.dto.CategoryDto;
import dev.antonis.spring_lab2.entity.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 1000))
    @Cacheable("category")
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::fromCategory).toList();
    }

    @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 1000))
    @Cacheable("categoryId")
    public CategoryDto getCategoryById(Integer id)  {
        return categoryRepository.findById(id)
                .map(category -> new CategoryDto(category.getName(), category.getSymbol(),category.getDescription()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    public int addCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.name())) {
            throw new IllegalArgumentException("A category with this name already exists: " + categoryDto.name());
        }

        Category category = new Category();
        category.setName(categoryDto.name());
        category.setSymbol(categoryDto.symbol());
        category.setDescription(categoryDto.description());
        category = categoryRepository.save(category);
        return  category.getId();
    }
}
