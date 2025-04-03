//package cn.edu.xidian.aws.constant;
//
//import lombok.Getter;
//
//import java.util.Arrays;
//
///**
// * @author akynazh@gmail.com
// * @date 2025/4/3
// * @description
// */
//@Getter
//public enum RecordStatus {
//    OK(1, "已处理"),
//    TODO(0, "待处理");
//
//    private final int code;
//    private final String message;
//    public static final String label = "称重记录状态";
//
//    RecordStatus(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public static boolean codeExists(int code) {
//        return Arrays.stream(RecordStatus.values()).anyMatch(s -> s.getCode() == code);
//    }
//
//    public static RecordStatus fromCode(int code) {
//        return Arrays.stream(RecordStatus.values()).filter(s -> s.getCode() == code).findFirst().orElse(null);
//    }
//}
