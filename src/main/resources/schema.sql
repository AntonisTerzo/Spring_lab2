CREATE TABLE IF NOT EXISTS category
(
    id          INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name        VARCHAR(255)       NOT NULL,
    symbol      VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    description VARCHAR(255)       NOT NULL
);

CREATE TABLE IF NOT EXISTS location
(
    id            INT AUTO_INCREMENT      NOT NULL PRIMARY KEY,
    name          VARCHAR(255)            NOT NULL,
    category_id   INT                     NOT NULL,
    user_id       INT                     NOT NULL,
    is_private     BOOLEAN   DEFAULT false NOT NULL,
    description   VARCHAR(255)            NOT NULL,
    coordinate    GEOMETRY                NOT NULL SRID 4326,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    latest_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(id)
);