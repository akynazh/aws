package cn.edu.xidian.aws.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akynazh@gmail.com
 * @date 2025/4/1
 * @description
 */
@Getter
@AllArgsConstructor
public enum Produces {

    APPLE(0, "苹果", "apple"),
    BANANA(1, "香蕉", "banana"),
    BELL_PEPPER(2, "彩椒", "bell_pepper"),
    CABBAGE(3, "卷心菜", "cabbage"),
    CARROT(4, "胡萝卜", "carrot"),
    CHILLI_PEPPER(5, "辣椒", "chilli_pepper"),
    CORN(6, "玉米", "corn"),
    CUCUMBER(7, "黄瓜", "cucumber"),
    EGGPLANT(8, "茄子", "eggplant"),
    GARLIC(9, "大蒜", "garlic"),
    GRAPE(10, "葡萄", "grape"),
    KIWI(11, "猕猴桃", "kiwi"),
    LEMON(12, "柠檬", "lemon"),
    LETTUCE(13, "生菜", "lettuce"),
    MANGO(14, "芒果", "mango"),
    ONION(15, "洋葱", "onion"),
    ORANGE(16, "橙子", "orange"),
    PINEAPPLE(17, "菠萝", "pineapple"),
    POTATO(18, "土豆", "potato"),
    SWEETPOTATO(19, "甘薯", "sweetpotato"),
    TOMATO(20, "番茄", "tomato"),
    WATERMELON(21, "西瓜", "watermelon");


    // 用来存储 index 到 enum 实例的映射
    private static final Map<Integer, Produces> CM = new HashMap<>();

    static {
        for (Produces item : Produces.values()) {
            CM.put(item.getClazz(), item);
        }
    }

    public static Produces get(int clazz) {
        return CM.get(clazz);
    }

    private final int clazz;
    private final String name;
    private final String nameEn;
}