package com.yupi.springbootinit.scoring;

import com.yupi.springbootinit.model.entity.App;
import com.yupi.springbootinit.model.entity.UserAnswer;

import java.util.List;

public interface ScoringStrategy {
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}
