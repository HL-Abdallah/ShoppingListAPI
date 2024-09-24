CREATE TABLE brand (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price BIGINT,
    calories BIGINT,
    brand_id BIGINT,
    FOREIGN KEY (brand_id) REFERENCES brand(id)
);

CREATE TABLE Person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    email VARCHAR(255)
);