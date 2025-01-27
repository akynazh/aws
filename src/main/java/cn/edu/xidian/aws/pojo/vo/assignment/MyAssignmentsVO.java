package cn.edu.xidian.aws.pojo.vo.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 1/27/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyAssignmentsVO {
    private List<Long> workIds;
}
