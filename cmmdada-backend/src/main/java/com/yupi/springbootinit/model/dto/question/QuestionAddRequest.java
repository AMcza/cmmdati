package com.yupi.springbootinit.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建题目请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class QuestionAddRequest implements Serializable {

    /**
     * 题目内容（json格式） ***注意返回类的设计***
     */
    private List<QuestionContentDto> questionContent;

    /**
     * 应用 id
     */
    private Long appId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}