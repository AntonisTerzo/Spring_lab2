package dev.antonis.spring_lab2.category;

import dev.antonis.spring_lab2.entity.Category;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {

    boolean existsByName(String name);
}
