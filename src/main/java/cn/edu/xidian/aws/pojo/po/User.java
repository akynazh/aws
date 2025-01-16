package cn.edu.xidian.aws.pojo.po;

import cn.edu.xidian.aws.pojo.vo.user.UserVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 用户
 */
@Entity
@Table(name = "t_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 身份证号
     */
    @Column(unique = true)
    private String uid;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 密码，需要加密
     */
    private String password;
    /**
     * 角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN
     */
    private String roles;
    /**
     * 创建时间，毫秒级时间戳
     */
    private long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    private long updateTime;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    private int status;

    public static UserVO toUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
