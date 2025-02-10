package cn.edu.xidian.aws.pojo.vo.produce;

import cn.edu.xidian.aws.constant.ScaleUnit;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.pojo.vo.work.WorkVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2/8/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProduceWorkOutputVO {
    private String name;
    private Long workId;
    private BigDecimal dataValue;
    private String unit;

    public static ProduceWorkOutputVO build(Produce produce, Work vo) {
        ProduceWorkOutputVO outputVO = new ProduceWorkOutputVO();
        outputVO.setName(produce.getName());
        outputVO.setWorkId(vo.getId());
        outputVO.setDataValue(vo.getDataValue());
        outputVO.setUnit(ScaleUnit.valueOf(vo.getUnit()).getMessage());
        return outputVO;
    }
}
