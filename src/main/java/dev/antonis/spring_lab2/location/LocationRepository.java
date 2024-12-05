package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.entity.Location;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {
    List<Location> findByIsPrivateFalse();
}
