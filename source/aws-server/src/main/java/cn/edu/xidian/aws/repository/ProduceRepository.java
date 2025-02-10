package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.Produce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description
 */
@Repository
public interface ProduceRepository extends JpaRepository<Produce, Long> {
    Optional<Produce> findByName(String name);
}
