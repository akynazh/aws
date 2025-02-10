package cn.edu.xidian.aws.pojo.vo.produce;

import cn.edu.xidian.aws.constant.ScaleUnit;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.util.ScaleUtil;
import cn.edu.xidian.aws.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2/8/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProduceAnnualOutputVO {
    private String name;
    private Integer year;
    private BigDecimal dataValue;
    private String unit;

    public static List<ProduceAnnualOutputVO> build(Produce produce, List<Work> works) {
        ScaleUnit KG = ScaleUnit.KG;
        List<ProduceAnnualOutputVO> vos = new ArrayList<>();
        Map<Integer, List<Work>> annualWorks = works.stream().collect(Collectors.groupingBy(work -> TimeUtil.getYear(work.getStartTime())));
        annualWorks.forEach((year, yearWorks) -> {
            ProduceAnnualOutputVO vo = new ProduceAnnualOutputVO();
            vo.setName(produce.getName());
            vo.setYear(year);
            BigDecimal total = yearWorks.stream().map(work -> ScaleUtil.convDataValue(work.getDataValue(), work.getUnit(), KG.getCode()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            vo.setDataValue(total);
            vo.setUnit(KG.getMessage());
            vos.add(vo);
        });
        return vos;
    }
}
