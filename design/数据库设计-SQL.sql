create table t_mqtt_acl
(
    id         int auto_increment
        primary key,
    action     varchar(255) not null,
    permission varchar(255) not null,
    qos        int          null,
    retain     int          null,
    topic      varchar(255) not null,
    username   varchar(255) not null
);

create table t_produce
(
    id          bigint       not null
        primary key,
    create_time bigint       not null,
    name        varchar(255) not null,
    name_en     varchar(255) null,
    status      int          not null,
    update_time bigint       not null,
    constraint UKeiu0gl8n19ntvbgc5i5ukaej2
        unique (name_en),
    constraint UKq8an6fogth2xm1mo7e7pgndav
        unique (name)
);

create table t_record
(
    id                bigint auto_increment
        primary key,
    data_error_margin decimal(10, 2) null,
    data_time         bigint         not null,
    data_value        decimal(10, 2) not null,
    employee_id       bigint         not null,
    image             varchar(255)   null,
    produce_id        bigint         not null,
    scale_id          bigint         not null,
    unit              int            not null,
    work_id           bigint         not null,
    constraint UKlgtm88ducdt1scborhio1r6ky
        unique (scale_id, employee_id, data_time)
);

create index IDX3isox1oymsuxkdpccqc31tich
    on t_record (scale_id);

create index IDX5biju9bncs9erljyh31w58vv1
    on t_record (employee_id);

create index IDX91np2r95e23t6hoyswukn3r3f
    on t_record (employee_id, work_id);

create index IDXq6ryn6hugk5ny4rtrltxyuexa
    on t_record (work_id);

create table t_scale
(
    id                    bigint auto_increment
        primary key,
    create_time           bigint         not null,
    display_interval      int            null,
    max_capacity          decimal(10, 2) not null,
    min_capacity          decimal(10, 2) not null,
    model                 varchar(255)   null,
    protocol              varchar(255)   null,
    sid                   varchar(255)   not null,
    status                int            not null,
    unit                  int            not null,
    unit_dv               int            null,
    update_time           bigint         not null,
    verification_interval int            null
);

create table t_todo
(
    id                bigint auto_increment
        primary key,
    data_error_margin decimal(10, 2) null,
    data_time         bigint         not null,
    data_value        decimal(10, 2) not null,
    employee_id       bigint         not null,
    image             varchar(255)   null,
    produce_id        bigint         null,
    produce_name      varchar(255)   null,
    scale_id          bigint         not null,
    unit              int            not null
);

create table t_user
(
    id          bigint auto_increment
        primary key,
    create_time bigint       not null,
    name        varchar(255) not null,
    password    varchar(255) not null,
    roles       varchar(255) null,
    status      int          not null,
    uid         varchar(255) not null,
    update_time bigint       not null,
    constraint UKcc1ycrtpjpjnkjsfml72vj5op
        unique (uid)
);

create table t_work
(
    id          bigint auto_increment
        primary key,
    create_time bigint         not null,
    data_value  decimal(10, 2) null,
    end_time    bigint         not null,
    produce_id  bigint         not null,
    start_time  bigint         not null,
    status      int            not null,
    unit        int            null,
    update_time bigint         not null
);

create index IDXdcmwaouoa9603wafnf9iwr8dg
    on t_work (produce_id);
