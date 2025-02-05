package cn.edu.xidian.aws.pojo.vo.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "AssignmentVO", description = "作业单个分配信息")
public class AssignmentVO {

    @Schema(description = "作业分配信息编号")
    private long id;

    /**
     * 采摘作业编号
     */
    @Schema(description = "采摘作业编号")
    private long workId;
    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private long employeeId;
    /**
     * 创建时间，毫秒级时间戳
     */
    @Schema(description = "创建时间，毫秒级时间戳")
    private long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    @Schema(description = "更新时间，毫秒级时间戳")
    private long updateTime;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    @Schema(description = "状态，0 为禁用，1 为启用，2 为已删除")
    private int status;
}
