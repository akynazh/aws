package cn.edu.xidian.aws.constant;

/**
 * @author akynazh@gmail.com
 * @date 2/26/25
 * @description
 */
public interface Mqtt {
    String PERMISSION_ALLOW = "allow";
    String PERMISSION_DENY = "deny";
    String ACTION_SUBSCRIBE = "subscribe";
    String ACTION_PUBLISH = "publish";
    String ACTION_ALL = "all";
    String TOPIC_ALL = "#";
    String TOPIC_SCALE = "t/scale";
    String TOPIC_RESULT = "t/result";
    int QOS_AT_MOST_ONCE = 0;
    int QOS_AT_LEAST_ONCE = 1;
    int QOS_EXACTLY_ONCE = 2;

    String AUTH_ALLOW = "allow";
    String AUTH_DENY = "deny";
}
