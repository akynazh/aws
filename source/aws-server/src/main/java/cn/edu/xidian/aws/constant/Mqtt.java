package cn.edu.xidian.aws.constant;

/**
 * @author akynazh@gmail.com
 * @date 2/26/25
 * @description
 */
public class Mqtt {
    public static final String PERMISSION_ALLOW = "allow";
    public static final String PERMISSION_DENY = "deny";
    public static final String ACTION_SUBSCRIBE = "subscribe";
    public static final String ACTION_PUBLISH = "publish";
    public static final String ACTION_ALL = "all";
    public static final String TOPIC_ALL = "#";
    public static final String TOPIC_SCALE = "t/scale";
    public static final int QOS_AT_MOST_ONCE = 0;
    public static final int QOS_AT_LEAST_ONCE = 1;
    public static final int QOS_EXACTLY_ONCE = 2;
    public static final int IS_SUPERUSER = 1;
    public static final int NOT_SUPERUSER = 0;
    public static final String HASH_ALGORITHM = "SHA-256";
}
