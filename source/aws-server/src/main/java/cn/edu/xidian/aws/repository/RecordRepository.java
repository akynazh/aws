package cn.edu.xidian.aws.repository;

import cn.edu.xidian.aws.pojo.dto.UserWorkOutputDTO;
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
    @Query(value = "select u.name as name, r.work_id as work_id, p.name as produce_name, sum(r.data_value) as data_value, r.unit as unit " +
            "from t_record r " +
            "left join t_work w on r.work_id = w.id " +
            "left join t_produce p on w.produce_id = p.id " +
            "left join t_user u on u.id = r.employee_id " +
            "where r.employee_id = :id " +
            "group by r.work_id, r.unit;",
            nativeQuery = true)
    List<UserWorkOutputDTO> getUserWorkSummary(@Param("id") Long id);

    Record getRecordByScaleIdAndEmployeeIdAndDataTime(Long scaleId, Long employeeId, Long dataTime);
}
