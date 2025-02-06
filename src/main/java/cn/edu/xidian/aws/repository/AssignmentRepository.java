package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.Assignment;
import cn.edu.xidian.aws.pojo.po.Produce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByWorkId(long workId);
    List<Assignment> findAllByWorkIdAndStatus(long workId, int status);

    List<Assignment> findAllByEmployeeId(long id);
}
