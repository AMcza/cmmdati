package com.yupi.springbootinit.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionContentDto {
    /**
     *题目选项
     */
    private List<Option> options; // 选项列表
    /**
     * 题目标题
     */
    private String title;         // 题目
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        private String result; // 如果是测评类，则用 reslut 来保存答案属性
        private int score;     // 如果是得分类，则用 score 来设置本题分数
        private String value;  // 选项内容
        private String key;    // 选项 key
    }
}
