CREATE DATABASE IF NOT EXISTS aws;

CREATE t_farm (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255)
)

CREATE t_user (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    farm_id INT
);