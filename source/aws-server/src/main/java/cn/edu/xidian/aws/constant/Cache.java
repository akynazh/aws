package cn.edu.xidian.aws.constant;

import cn.edu.xidian.aws.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author akynazh@gmail.com
 * @date 2025/3/28
 * @description
 */
@Getter
@AllArgsConstructor
public enum Cache {
    USER_WORK_SUMMARY("user:work:summary:", 1L, TimeUnit.HOURS),
    PRODUCE_ANNUAL_OUTPUT("produce:annual:output:", 7L, TimeUnit.DAYS),
    PRODUCE_WORK_OUTPUT("produce:work:output:", 1L, TimeUnit.DAYS),
    RECORD_TEMP("record:temp:", null, null);

    private final String prefix;
    private final Long duration;
    private final TimeUnit unit;
}
