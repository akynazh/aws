package cn.edu.xidian.aws.pojo.vo.produce;

import cn.edu.xidian.aws.pojo.vo.record.RecordVO;
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
@Schema(name = "ProduceListVO", description = "农产品列表")
public class ProduceListVO {
    private List<ProduceVO> produceList;
    private long count;
}
