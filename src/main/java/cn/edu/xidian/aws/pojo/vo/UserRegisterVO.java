package cn.edu.xidian.aws.pojo.vo;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
public class UserRegisterVO {
    private String uid;
    private String name;
    private String cid;

    public UserRegisterVO() {
    }

    public UserRegisterVO(String uid, String name, String cid) {
        this.uid = uid;
        this.name = name;
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserRegisterVO{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
