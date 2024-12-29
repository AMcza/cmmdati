package com.yupi.springbootinit.model.enums;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * App应用类型枚举
 */
public enum AppTypeEnum {
    SCORE(0, "得分类"),
    TEST(1, "测评类");
    private int value;
    private String text;

    AppTypeEnum(int code, String desc) {
        this.value = code;
        this.text = desc;
    }
    //根据value获取枚举对象
    public static AppTypeEnum getEnumByValue(int value) {
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for (AppTypeEnum statusEnum : AppTypeEnum.values()) {
            if (statusEnum.value== value) {
                return statusEnum;
            }
        }
        return null;
    }
    /**
     * 根据所有值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
    public int getValue() {
        return value;
    }
    public String getText() {
        return text;
    }
}
