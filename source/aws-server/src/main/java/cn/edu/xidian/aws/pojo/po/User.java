package cn.edu.xidian.aws.pojo.po;

import cn.edu.xidian.aws.constant.UserRole;
import cn.edu.xidian.aws.pojo.vo.user.UserVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

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
    private Long id;

    /**
     * 身份证号
     */
    @Column(unique = true, nullable = false)
    private String uid;

    /**
     * 员工姓名
     */
    @Column(nullable = false)
    private String name;

    /**
     * 密码，需要加密
     */
    @Column(nullable = false)
    private String password;

    /**
     * 角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN
     */
    private String roles;

    /**
     * 创建时间，毫秒级时间戳
     */
    @Column(nullable = false)
    private Long createTime;

    /**
     * 更新时间，毫秒级时间戳
     */
    @Column(nullable = false)
    private Long updateTime;

    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    @Column(nullable = false)
    private Integer status;

    public static UserVO toUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
