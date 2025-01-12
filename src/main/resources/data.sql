-- add foreign key checks so it doesn't crush
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE location;
TRUNCATE TABLE category;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO category(name, symbol, description)
VALUES ('Loved Places', '☀️', 'My loved places'),
       ('Places i want to visit', '🚀', 'Places i want to visit in the next 5 years'),
       ('Places i lived', '🏠', 'Places i lived in the past 5 years');

INSERT INTO location(name, category_id, user_id, is_private, description, coordinates)
VALUES ('Trafford Park', 1, 1, false, 'Best place in the world',
        ST_GeomFromText('POINT(53.463309 -2.292249)', 4326)),
       ('Matamata', 2, 2, false, 'The set of the lord of the rings',
        ST_GeomFromText('POINT(-37.872095 175.683095)', 4326)),
       ('Kalmar', 3, 3, true, 'Places i lived in',
        ST_GeomFromText('POINT(56.685633 16.335170)', 4326)),
       ('Rodopoli', 3, 4, true, 'My beloved village',
        ST_GeomFromText('POINT(41.261477 23.000143)', 4326)),
       ('Miami', 2, 5, false, 'Miami city, aka paradise',
        ST_GeomFromText('POINT(25.766418 -80.192207)', 4326));
