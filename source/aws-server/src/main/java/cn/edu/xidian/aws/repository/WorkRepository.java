package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description
 */
@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByProduceId(Long produceId);
    List<Work> findAllByStatus(Integer status);
    List<Work> findAllByStatusIn(List<Integer> status);
}
