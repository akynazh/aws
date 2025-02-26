package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.MqttUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author akynazh@gmail.com
 * @date 2/25/25
 * @description
 */
@Repository
public interface MqttUserRepository extends JpaRepository<MqttUser, Integer> {
    Optional<MqttUser> findByUsername(String username);
}
