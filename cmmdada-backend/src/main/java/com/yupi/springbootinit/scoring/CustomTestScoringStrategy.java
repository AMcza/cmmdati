package com.yupi.springbootinit.scoring;
import java.util.*;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yupi.springbootinit.model.dto.question.QuestionContentDto;
import com.yupi.springbootinit.model.entity.App;
import com.yupi.springbootinit.model.entity.Question;
import com.yupi.springbootinit.model.entity.ScoringResult;
import com.yupi.springbootinit.model.entity.UserAnswer;
import com.yupi.springbootinit.model.vo.QuestionVO;
import com.yupi.springbootinit.service.AppService;
import com.yupi.springbootinit.service.QuestionService;
import com.yupi.springbootinit.service.ScoringResultService;

import javax.annotation.Resource;

/**
 * 自定义测评类题目应用评分策略
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomTestScoringStrategy implements ScoringStrategy{
    @Resource
    private QuestionService questionService;
    @Resource
    private AppService appService;
    @Resource
    private ScoringResultService scoringResultService;
    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        Long appId = app.getId();

        // 1. 根据id查询题目和题目结果信息
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class).eq(ScoringResult::getAppId, appId)
        );

        // 2. 将题目选项的结果映射到Map中，便于快速查找
        QuestionVO questionVO = QuestionVO.objToVo(question);
        Map<String, String> optionResultMap = new HashMap<>();
        for (QuestionContentDto questionContentDto : questionVO.getQuestionContent()) {
            for (QuestionContentDto.Option option : questionContentDto.getOptions()) {
                optionResultMap.put(option.getKey(), option.getResult());
            }
        }

        // 3. 统计用户每个选择对应的属性个数
        Map<String, Integer> optionCount = new HashMap<>();
        for (String choice : choices) {
            String result = optionResultMap.get(choice);
            if (result != null) {
                optionCount.merge(result, 1, Integer::sum);
            }
        }

        // 4. 计算哪个结果的得分更高
        ScoringResult maxScoringResult = scoringResultList.stream()
                .max(Comparator.comparingInt(scoringResult ->
                        JSONUtil.toList(scoringResult.getResultProp(), String.class)
                                .stream()
                                .mapToInt(prop -> optionCount.getOrDefault(prop, 0))
                                .sum()
                ))
                .orElse(null);

        // 5. 返回用户得分结果
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));

        if (maxScoringResult != null) {
            userAnswer.setResultId(maxScoringResult.getId());
            userAnswer.setResultName(maxScoringResult.getResultName());
            userAnswer.setResultDesc(maxScoringResult.getResultDesc());
            userAnswer.setResultPicture(maxScoringResult.getResultPicture());
        }

        return userAnswer;
    }
}
