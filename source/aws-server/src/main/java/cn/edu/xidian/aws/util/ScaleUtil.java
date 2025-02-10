package cn.edu.xidian.aws.util;

import cn.edu.xidian.aws.constant.ScaleStatus;
import cn.edu.xidian.aws.constant.ScaleUnit;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2/7/25
 * @description
 */
public class ScaleUtil {
    public static BigDecimal convDataValue(BigDecimal dataValue, int unit, int targetUnit) {
        if (unit == targetUnit) {
            return dataValue;
        }
        ScaleUnit scaleUnit = ScaleUnit.valueOf(unit);
        ScaleUnit targetScaleUnit = ScaleUnit.valueOf(targetUnit);
        double times = (double) scaleUnit.getTimes() / targetScaleUnit.getTimes();
        return dataValue.multiply(BigDecimal.valueOf(times));
    }
}
