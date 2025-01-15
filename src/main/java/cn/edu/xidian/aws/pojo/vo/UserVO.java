package cn.edu.xidian.aws.pojo.vo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVO {
    private long id;
    private String uid;
    private String name;
    private String roles;
    private long createTime;
    private long updateTime;
    private int status = -1;
}
