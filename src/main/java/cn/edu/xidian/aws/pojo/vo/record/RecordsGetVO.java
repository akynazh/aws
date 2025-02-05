package cn.edu.xidian.aws.pojo.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 1/28/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "RecordsGetVO", description = "称重记录查询参数")
public class RecordsGetVO {
    @Schema(description = "当前页码")
    int page;
    @Schema(description = "每页记录数")
    int size;
    @Schema(description = "采摘作业编号")
    private long workId;
    @Schema(description = "员工编号")
    private long employeeId;
    @Schema(description = "电子秤编号")
    private long scaleId;
}
