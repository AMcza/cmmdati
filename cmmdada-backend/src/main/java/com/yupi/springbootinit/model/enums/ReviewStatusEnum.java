package com.yupi.springbootinit.model.enums;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 审核状态枚举
 * @author cmm
 * @date 2024/11/24
 */
public enum ReviewStatusEnum {
    REVIEWING("带审核",0),
    PASS("通过",1),
    REJECT("拒绝",2);
    private final String text;
    private final int value;

    ReviewStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }
    //根据value获取枚举对象
    public static ReviewStatusEnum getEnumByValue(int value) {
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for (ReviewStatusEnum statusEnum : ReviewStatusEnum.values()) {
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
