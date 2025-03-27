package cn.edu.xidian.aws.pojo.vo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/3/26
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "EmqxAuthResultVO", description = "EMQX 用户认证结果")
public class EmqxAuthResultVO {
    /**
     * "result": "allow", // "allow" | "deny" | "ignore"
     */
    private String result;
    /**
     * "is_superuser": true, // true | false，该项为空时默认为 false
     */
    @JsonProperty("is_superuser")
    private Boolean isSuperuser;
}
