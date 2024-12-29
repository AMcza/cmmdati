package com.yupi.springbootinit.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class ReviewRequest implements Serializable {
    private Long id;
    private Integer reviewStatus;
    private String reviewMessage;
    private static final long serialVersionUID = 1L;
}
