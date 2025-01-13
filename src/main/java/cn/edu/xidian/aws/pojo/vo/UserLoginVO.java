package cn.edu.xidian.aws.pojo.vo;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
public class UserLoginVO {
    private String uid;
    private String password;

    public UserLoginVO() {
    }

    public UserLoginVO(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginVO{" +
                "uid='" + uid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
