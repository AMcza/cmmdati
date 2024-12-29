package com.yupi.springbootinit.model.dto.question;


import lombok.Data;

import java.io.Serializable;

@Data
public class AiGenerateQuestionRequest implements Serializable {
    private Long appId;
    int questionNumber=10;
    /**
     * 选项数
     */
    int optionNumber=2;
    private static final long serialVersionUID=1L;
}
