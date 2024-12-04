package dev.antonis.spring_lab2.category;

import dev.antonis.spring_lab2.category.dto.CategoryDto;
import dev.antonis.spring_lab2.entity.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::fromCategory).toList();
    }

    public CategoryDto getCategoryById(Integer id)  {
        return categoryRepository.findById(id)
                .map(category -> new CategoryDto(category.getName(), category.getSymbol(),category.getDescription()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    public int addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.name());
        category.setSymbol(categoryDto.symbol());
        category.setDescription(categoryDto.description());
        category = categoryRepository.save(category);
        return  category.getId();
    }
}
