package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.po.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description
 */
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "SELECT * FROM t_record WHERE id = ?1", nativeQuery = true)
    List<Record> findRecords(@Param("id") long id);
}
