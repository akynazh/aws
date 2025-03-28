package cn.edu.xidian.aws.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2/25/25
 * @description
 */
@Entity
@Table(name = "t_mqtt_acl")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MqttAcl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String permission;
    private String action;
    private String topic;
    private Integer qos;
    private Integer retain;
}