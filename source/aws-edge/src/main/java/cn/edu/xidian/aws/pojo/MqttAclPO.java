package cn.edu.xidian.aws.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2/25/25
 * @description -- 还需要在 emqx dashboard 配置 SQL
 * -- SELECT permission, action, topic, qos, retain FROM t_mqtt_acl WHERE username = ${username}
 */
@Entity
@Table(name = "t_mqtt_acl")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MqttAclPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Mqtt 用户名
     */
    @Column(nullable = false)
    private String username;

    /**
     * 用于指定操作权限，可选值有 allow 和 deny
     */
    @Column(nullable = false)
    private String permission;

    /**
     * 用于指定当前规则适用于哪些操作，可选值有 publish、subscribe 和 all
     */
    @Column(nullable = false)
    private String action;

    /**
     * 用于指定当前规则适用的主题，可以使用主题过滤器和主题占位符。(# 代表所有)
     */
    @Column(nullable = false)
    private String topic;

    /**
     * (可选)用于指定规则适用的消息 QoS，可选值为 0、1、2，也可以用 , 分隔的字符串指定多个 QoS，例如 0,1。默认为全部 QoS
     */
    private Integer qos;

    /**
     * （可选）用于指定当前规则是否支持发布保留消息，可选值有 0、1，默认允许保留消息。
     */
    private Integer retain;
}