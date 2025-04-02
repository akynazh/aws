package cn.edu.xidian.aws.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/3/29
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAuthResultVO {
    private String result;
    @JsonProperty("is_superuser")
    private Boolean isSuperuser = Boolean.FALSE;

    public UserAuthResultVO(String result) {
        this.result = result;
    }
}
