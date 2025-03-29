package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description
 */
@Repository
public interface UserRepository extends JpaRepository<UserPO, Long> {
    Optional<UserPO> findByUid(String uid);
}
