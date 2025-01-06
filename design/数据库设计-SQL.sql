CREATE DATABASE IF NOT EXISTS aws;

CREATE t_employee (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
);
CREATE t_admin (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255)
);
CREATE t_scale (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    key VARCHAR(255)
);
CREATE t_produce (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);
CREATE t_work (
    id INT PRIMARY KEY,
    produce_id INT,
    start_time INT,
    end_time INT,
    result INT
);
CREATE t_record (
    id INT PRIMARY KEY,
    work_id INT,
    employee_id INT,
    scale_id INT,
    record_time INT,
    record_data DOUBLE
);

CREATE t_work_dis (
    id INT PRIMARY KEY,
    work_id INT,
    employee_id INT
);
