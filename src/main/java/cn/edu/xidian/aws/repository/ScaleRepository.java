package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.po.Scale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description
 */
@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long> {
}
