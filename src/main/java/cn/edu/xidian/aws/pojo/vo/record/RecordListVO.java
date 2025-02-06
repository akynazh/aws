package cn.edu.xidian.aws.pojo.vo.record;

import cn.edu.xidian.aws.pojo.vo.scale.ScaleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2/5/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "RecordListVO", description = "称重记录列表")
public class RecordListVO {
    private List<RecordVO> recordList;
    private long count;
}
