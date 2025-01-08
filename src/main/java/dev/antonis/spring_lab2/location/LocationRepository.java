package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.entity.Location;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {
    List<Location> findByIsPrivateFalse();
    // Get public locations by id
    Optional<Location> findByIdAndIsPrivateFalse(Integer id);
    // Get public locations in a category
    List<Location> findByCategory_IdAndIsPrivateFalse(Integer categoryId);
    List<Location> findByUserId(Integer userId);
}
