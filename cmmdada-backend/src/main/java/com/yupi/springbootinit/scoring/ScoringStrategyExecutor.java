package com.yupi.springbootinit.scoring;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.App;
import com.yupi.springbootinit.model.entity.UserAnswer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScoringStrategyExecutor {
    @Resource
    private List<ScoringStrategy> scoringStrategyList;
    public UserAnswer doScore(List<String> choiceslist, App app)throws Exception {
        Integer appType=app.getAppType();
        Integer appScoringStrategy=app.getScoringStrategy();
        if(appType==null || appScoringStrategy==null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误");
        }
        //根据注解获取注解策略
        for(ScoringStrategy strategy:scoringStrategyList){
            if(strategy.getClass().isAnnotationPresent(ScoringStrategyConfig.class)){
                ScoringStrategyConfig scoringStrategyConfig = strategy.getClass().getAnnotation(ScoringStrategyConfig.class);
                if(scoringStrategyConfig.appType()==appType && scoringStrategyConfig.scoringStrategy()==appScoringStrategy){
                    return strategy.doScore(choiceslist,app);
                }
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误");
    }
}
