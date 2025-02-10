package cn.edu.xidian.aws.pojo.vo.scale;

import cn.edu.xidian.aws.pojo.vo.user.UserVO;
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
@Schema(name = "ScaleListVO", description = "电子秤列表")
public class ScaleListVO {
    private List<ScaleVO> scaleList;
    private Long count;
}
