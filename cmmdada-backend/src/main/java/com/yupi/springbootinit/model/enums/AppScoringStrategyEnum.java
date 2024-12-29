package com.yupi.springbootinit.model.enums;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * App应用类型枚举
 */
public enum AppScoringStrategyEnum {
    CUSTOM(0, "自定义"),
    AI(1, "AI");
    private int value;
    private String text;

    AppScoringStrategyEnum(int code, String desc) {
        this.value = code;
        this.text = desc;
    }
    //根据value获取枚举对象
    public static AppScoringStrategyEnum getEnumByValue(int value) {
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for (AppScoringStrategyEnum statusEnum : AppScoringStrategyEnum.values()) {
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
