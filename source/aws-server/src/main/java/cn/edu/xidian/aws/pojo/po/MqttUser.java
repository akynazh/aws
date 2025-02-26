package cn.edu.xidian.aws.pojo.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author akynazh@gmail.com
 * @date 2/25/25
 * @description
 */
@Entity
@Table(name = "t_mqtt_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MqttUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String passwordHash;
    private String salt;
    private Integer isSuperuser;
    private LocalDateTime created;
}
