package cn.edu.xidian.aws.pojo.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/3/24
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProduceRecResult {
    @JsonProperty("result_num")
    private Long resultNum;
    @JsonProperty("result")
    private List<ProduceRecScoreAndName> result;
}