CREATE TABLE `t_mqtt_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password_hash` varchar(100) DEFAULT NULL, -- SHA2(concat('${password}', '${salt}'), 256)
  `salt` varchar(35) DEFAULT NULL,
  `is_superuser` tinyint(1) DEFAULT 0,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mqtt_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- emqx dashboard 配置 SQL
-- SELECT password_hash, salt, is_superuser FROM t_mqtt_user WHERE username = ${username} LIMIT 1

CREATE TABLE `t_mqtt_acl` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `permission` varchar(5) NOT NULL, -- 用于指定操作权限，可选值有 allow 和 deny。
  `action` varchar(9) NOT NULL, -- 用于指定当前规则适用于哪些操作，可选值有 publish、subscribe 和 all。
  `topic` varchar(100) NOT NULL, -- 用于指定当前规则适用的主题，可以使用主题过滤器和主题占位符。(# 代表所有)
  `qos` tinyint(1), -- (可选)用于指定规则适用的消息 QoS，可选值为 0、1、2，也可以用 , 分隔的字符串指定多个 QoS，例如 0,1。默认为全部 QoS。
  `retain` tinyint(1), -- （可选）用于指定当前规则是否支持发布保留消息，可选值有 0、1，默认允许保留消息。
  INDEX username_idx(username),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- emqx dashboard 配置 SQL
-- SELECT permission, action, topic, qos, retain FROM t_mqtt_acl WHERE username = ${username}

INSERT INTO t_mqtt_user(username, password_hash, salt, is_superuser, created) VALUES ('aws-server', SHA2(concat('123456', 'slat_foo123'), 256), 'slat_foo123', 0, now());
INSERT INTO t_mqtt_acl(username, permission, action, topic) VALUES ('aws-server', 'allow', 'subscribe', 't/scale');
INSERT INTO t_mqtt_acl(username, permission, action, topic) VALUES ('aws-server', 'deny', 'all', '#');
