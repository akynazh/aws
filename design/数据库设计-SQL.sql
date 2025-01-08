CREATE DATABASE IF NOT EXISTS aws;

-- 基本表
CREATE TABLE
    IF NOT EXISTS t_user (
        id BIGINT PRIMARY KEY, -- 唯一编号，自动生成
        card_id VARCHAR(255), -- 员工卡 ID
        name VARCHAR(255), -- 名称
        email VARCHAR(255) UNIQUE, -- 邮箱
        password VARCHAR(255), -- 密码，需要加密
        roles VARCHAR(255), -- 角色，以逗号分隔
        create_time BIGINT, -- 创建时间，毫秒级时间戳
        update_time BIGINT, -- 更新时间，毫秒级时间戳
        status INT -- 状态，0 为禁用，1 为启用，2 为已删除
    );

CREATE TABLE
    IF NOT EXISTS t_scale (
        id BIGINT PRIMARY KEY, -- 唯一编号，自动生成
        model VARCHAR(255), -- 型号
        max_capacity DECIMAL(10, 2), -- 最大量程
        min_capacity DECIMAL(10, 2), -- 最小量程
        unit INT, -- 量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
        verification_interval DECIMAL(10, 2), -- 检定分度值
        display_interval DECIMAL(10, 2), -- 显示分度值
        unit_dv INT, -- 分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
        key VARCHAR(255) UNIQUE, -- 电子秤密钥
        protocal INT, -- 通信协议，0 为 MQTT，1 为 HTTP
        create_time BIGINT, -- 创建时间，毫秒级时间戳
        update_time BIGINT, -- 更新时间，毫秒级时间戳
        status INT -- 状态，0 为禁用，1 为启用，2 为已删除
    );

CREATE TABLE
    IF NOT EXISTS t_produce (
        id BIGINT PRIMARY KEY, -- 唯一编号，自动生成
        name VARCHAR(255) UNIQUE, -- 名称
        type INT UNIQUE, -- 农作物类型
        create_time BIGINT, -- 创建时间，毫秒级时间戳
        update_time BIGINT, -- 更新时间，毫秒级时间戳
        status INT -- 状态，0 为未种植，1 为在种植，2 为已删除
    );

CREATE TABLE
    IF NOT EXISTS t_work (
        id BIGINT PRIMARY KEY, -- 唯一编号，自动生成
        produce_id BIGINT, -- 农作物编号
        start_time BIGINT, -- 采摘开始时间，毫秒级时间戳
        end_time BIGINT, -- 采摘结束时间，毫秒级时间戳
        data_value DECIMAL(10, 2), -- 总的采摘称重结果
        data_unit INT, -- 称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
        create_time BIGINT, -- 创建时间，毫秒级时间戳
        update_time BIGINT, -- 更新时间，毫秒级时间戳
        status INT -- 状态，0 为未开始，1 为进行中，2 为已结束，3 为已取消，4 为已删除
    );
CREATE INDEX idx_t_work_produce ON t_work (produce_id);


CREATE TABLE
    IF NOT EXISTS t_record (
        id BIGINT PRIMARY KEY, -- 唯一编号，自动生成
        work_id BIGINT, -- 采摘作业编号
        employee_id BIGINT, -- 员工编号
        scale_id BIGINT, -- 电子秤编号
        data_value DECIMAL(10, 2), -- 称重结果
        data_error_margin DECIMAL(10, 2), -- 称量结果误差范围，正负多少
        data_unit INT, -- 称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
        data_time BIGINT, -- 称重时间，毫秒级时间戳
    );
CREATE INDEX idx_t_record_work ON t_record (work_id);
CREATE INDEX idx_t_record_employee ON t_record (employee_id);
CREATE INDEX idx_t_record_scale ON t_record (scale_id);
CREATE INDEX idx_t_record_employee_work ON t_record (employee_id, work_id);


-- 中间表
CREATE TABLE
    IF NOT EXISTS t_work_dis (
        id BIGINT PRIMARY KEY, -- 唯一编号，自动生成
        work_id BIGINT, -- 采摘作业编号
        employee_id BIGINT, -- 员工编号
        create_time BIGINT, -- 创建时间，毫秒级时间戳
        update_time BIGINT, -- 更新时间，毫秒级时间戳
        status INT -- 状态，0 为禁用，1 为启用，2 为已删除
    );
CREATE INDEX idx_t_work_dis_work ON t_work_dis (work_id);
CREATE INDEX idx_t_work_dis_employee ON t_work_dis (employee_id);
CREATE INDEX idx_t_work_dis_employee_work ON t_work_dis (employee_id, work_id);
