package com.yupi.springbootinit.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yupi.springbootinit.model.dto.question.QuestionContentDto;
import com.yupi.springbootinit.model.entity.App;
import com.yupi.springbootinit.model.entity.Question;
import com.yupi.springbootinit.model.entity.ScoringResult;
import com.yupi.springbootinit.model.entity.UserAnswer;
import com.yupi.springbootinit.model.vo.QuestionVO;
import com.yupi.springbootinit.service.QuestionService;
import com.yupi.springbootinit.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 自定义打分类题目应用评分策略
 */
@ScoringStrategyConfig(appType = 0, scoringStrategy = 0)
public class CustomScoreScoringStrategy implements ScoringStrategy{
    @Resource
    private QuestionService questionService;
    @Resource
    private ScoringResultService scoringResultService;
    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        Long appId = app.getId();
        //根据id查询题目和题目结果信息
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList=scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, appId)
                        .orderByAsc(ScoringResult::getResultScoreRange)
        );
        //统计用户总得分
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDto> questionContent=questionVO.getQuestionContent();
        //遍历题目列表
        int totalScore=0;
        for(QuestionContentDto questionContentDto:questionContent){
            //遍历答案列表
            for(String answer:choices){
                //遍历题目中的选项
                for(QuestionContentDto.Option option:questionContentDto.getOptions()){
                    //如果答案和选项的key匹配
                    if(option.getKey().equals(answer)){
                        String result=option.getResult();
                        int score= Optional.ofNullable(option.getScore()).orElse(0);
                        totalScore+=score;
                    }
                }
            }
        }
        //遍历得分结果,找到第一个用户分数大于得分范围结果，作为最终结果
        ScoringResult maxScoringResult=scoringResultList.get(0);

        for(ScoringResult scoringResult:scoringResultList){
            if(totalScore>=scoringResult.getResultScoreRange()){
                maxScoringResult=scoringResult;
                break;
            }
        }
        //结构返回值,填充答案对象属性
        UserAnswer userAnswer=new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());
        userAnswer.setResultScore(totalScore);
        return userAnswer;
    }
}
