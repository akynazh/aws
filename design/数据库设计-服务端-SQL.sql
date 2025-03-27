CREATE TABLE t_produce
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NULL,
    type        INT                   NULL,
    create_time BIGINT                NOT NULL,
    update_time BIGINT                NOT NULL,
    status      INT                   NOT NULL,
    CONSTRAINT pk_t_produce PRIMARY KEY (id)
);

CREATE TABLE t_record
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    work_id           BIGINT                NOT NULL,
    employee_id       BIGINT                NOT NULL,
    scale_id          BIGINT                NOT NULL,
    data_value        DECIMAL(10, 2)        NULL,
    data_error_margin DECIMAL(10, 2)        NULL,
    unit              INT                   NOT NULL,
    data_time         BIGINT                NOT NULL,
    CONSTRAINT pk_t_record PRIMARY KEY (id)
);

CREATE TABLE t_scale
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    skey                  VARCHAR(255)          NULL,
    model                 VARCHAR(255)          NULL,
    max_capacity          DECIMAL(10, 2)        NULL,
    min_capacity          DECIMAL(10, 2)        NULL,
    unit                  INT                   NOT NULL,
    verification_interval INT                   NOT NULL,
    display_interval      INT                   NOT NULL,
    unit_dv               INT                   NOT NULL,
    protocol              INT                   NOT NULL,
    create_time           BIGINT                NOT NULL,
    update_time           BIGINT                NOT NULL,
    status                INT                   NOT NULL,
    CONSTRAINT pk_t_scale PRIMARY KEY (id)
);

CREATE TABLE t_user
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    uid         VARCHAR(255)          NULL,
    cid         VARCHAR(255)          NULL,
    name        VARCHAR(255)          NULL,
    password    VARCHAR(255)          NULL,
    roles       VARCHAR(255)          NULL,
    create_time BIGINT                NOT NULL,
    update_time BIGINT                NOT NULL,
    status      INT                   NOT NULL,
    CONSTRAINT pk_t_user PRIMARY KEY (id)
);

CREATE TABLE t_work
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    produce_id  BIGINT                NOT NULL,
    start_time  BIGINT                NOT NULL,
    end_time    BIGINT                NOT NULL,
    data_value  DECIMAL(10, 2)        NULL,
    unit        INT                   NOT NULL,
    create_time BIGINT                NOT NULL,
    update_time BIGINT                NOT NULL,
    status      INT                   NOT NULL,
    CONSTRAINT pk_t_work PRIMARY KEY (id)
);

ALTER TABLE t_produce
    ADD CONSTRAINT uc_t_produce_name UNIQUE (name);

ALTER TABLE t_produce
    ADD CONSTRAINT uc_t_produce_type UNIQUE (type);

ALTER TABLE t_scale
    ADD CONSTRAINT uc_t_scale_skey UNIQUE (skey);

ALTER TABLE t_user
    ADD CONSTRAINT uc_t_user_cid UNIQUE (cid);

ALTER TABLE t_user
    ADD CONSTRAINT uc_t_user_uid UNIQUE (uid);

CREATE INDEX idx_853a0d80741b5033f24de6623 ON t_record (employee_id, work_id);

CREATE INDEX idx_9d65f7f0ba74b613b48315934 ON t_record (work_id);

CREATE INDEX idx_c8f83c4cdd003e5fb42b2ae65 ON t_record (employee_id);

CREATE INDEX idx_e65c826206e0bf0f5d4f8351e ON t_work (produce_id);

CREATE INDEX idx_f89fb714a035eaa647eeb53ef ON t_record (scale_id);
