package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.MqttAcl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author akynazh@gmail.com
 * @date 2/25/25
 * @description
 */
@Repository
public interface MqttAclRepository extends JpaRepository<MqttAcl, Integer> {
    boolean existsByUsername(String username);
}
