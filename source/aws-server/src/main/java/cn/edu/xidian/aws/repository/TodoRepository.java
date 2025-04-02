package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.po.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author akynazh@gmail.com
 * @date 2025/4/2
 * @description
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
