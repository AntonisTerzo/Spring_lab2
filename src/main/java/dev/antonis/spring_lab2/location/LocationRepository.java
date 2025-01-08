package dev.antonis.spring_lab2.location;

import dev.antonis.spring_lab2.entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {
    List<Location> findByIsPrivateFalse();
    // Get public locations by id
    Optional<Location> findByIdAndIsPrivateFalse(Integer id);
    // Get public locations in a category
    List<Location> findByCategory_IdAndIsPrivateFalse(Integer categoryId);
    // Get all location by user
    List<Location> findByUserId(Integer userId);
    // Get all locations in a radius
    @Query(value = """
       SELECT l
       FROM Location l
       WHERE FUNCTION('ST_Distance_Sphere', l.coordinate, FUNCTION('ST_GeomFromText', :point, 4326)) <= :radius
       """)
    List<Location> findWithinRadius(@Param("point") String point, @Param("radius") Double radius);
}


/*
FUNCTION('ST_Distance_Sphere', ...): Calls the MySQL spatial function ST_Distance_Sphere() to calculate the great-circle distance between two points.
FUNCTION('ST_GeomFromText', ...): Converts the input point in WKT format (POINT(lng lat)) into a geometry object compatible with MySQL's spatial functions.
Dynamic Parameters:
:point should be passed as POINT(lng lat) in WKT format, e.g., POINT(18.0632 59.3346).
:radius is the maximum distance (in meters).
 */