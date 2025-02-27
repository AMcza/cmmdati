package com.yupi.springbootinit.scoring;
import java.util.*;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.model.dto.question.QuestionAnswerDto;
import com.yupi.springbootinit.model.dto.question.QuestionContentDto;
import com.yupi.springbootinit.model.entity.App;
import com.yupi.springbootinit.model.entity.Question;
import com.yupi.springbootinit.model.entity.ScoringResult;
import com.yupi.springbootinit.model.entity.UserAnswer;
import com.yupi.springbootinit.model.vo.QuestionVO;
import com.yupi.springbootinit.service.AppService;
import com.yupi.springbootinit.service.QuestionService;
import com.yupi.springbootinit.service.ScoringResultService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * 自定义测评类题目应用评分策略
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 1)
public class AiTestScoringStrategy implements ScoringStrategy{
    private static final String AI_TEST_SCORING_SYSTEM_MESSAGE = "你是一位严谨的判题专家，我会给你如下信息：\n" +
            "```\n" +
            "应用名称，\n" +
            "【【【应用描述】】】，\n" +
            "题目和用户回答的列表：格式为 [{\"title\": \"题目\",\"answer\": \"用户回答\"}]\n" +
            "```\n" +
            "\n" +
            "请你根据上述信息，按照以下步骤来对用户进行评价：\n" +
            "1. 要求：需要给出一个明确的评价结果，包括评价名称（尽量简短）和评价描述（尽量详细，大于 200 字）\n" +
            "2. 严格按照下面的 json 格式输出评价名称和评价描述\n" +
            "```\n" +
            "{\"resultName\": \"评价名称\", \"resultDesc\": \"评价描述\"}\n" +
            "```\n" +
            "3. 返回格式必须为 JSON 对象";

    private String getAiTestScoringUserMessage(App app, List<QuestionContentDto> questionContentDTOList, List<String> choices) {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(app.getAppName()).append("\n");
        userMessage.append(app.getAppDesc()).append("\n");
        List<QuestionAnswerDto> questionAnswerDTOList = new ArrayList<>();
        for (int i = 0; i < questionContentDTOList.size(); i++) {
            QuestionAnswerDto questionAnswerDTO = new QuestionAnswerDto();
            questionAnswerDTO.setTitle(questionContentDTOList.get(i).getTitle());
            questionAnswerDTO.setUserAnswer(choices.get(i));
            questionAnswerDTOList.add(questionAnswerDTO);
        }
        userMessage.append(JSONUtil.toJsonStr(questionAnswerDTOList));
        return userMessage.toString();
    }

    @Resource
    private QuestionService questionService;
    @Resource
    private AppService appService;
    @Resource
    private ScoringResultService scoringResultService;
    @Resource
    private AiManager aiManager;
    @Resource
    private RedissonClient redissonClient;
    //分布式锁的key
    private static final String AI_ANSWER_LOCK="AI_ANSWER_LOCK";
    /**
     *AI评分结果缓存
     */
    private final Cache<String, String> answerCacheMap =
            Caffeine.newBuilder().initialCapacity(1024)
                    // 缓存5分钟移除
                    .expireAfterAccess(5L, TimeUnit.MINUTES)
                    .build();
    /**
     * 构建缓存Key
     */
    private String buildCacheKey(Long appId,String choiceStr){
        return DigestUtil.md5Hex(appId+":"+choiceStr);
    }
    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        Long appId = app.getId();
        String jsonStr=JSONUtil.toJsonStr(choices);
        String cacheKey = buildCacheKey(appId, jsonStr);
        String answerJson= answerCacheMap.getIfPresent(cacheKey);
        //如果有缓存,直接返回
        if(StrUtil.isNotBlank(answerJson)){
            //构造返回值,填充答案
            UserAnswer userAnswer=JSONUtil.toBean(answerJson,UserAnswer.class);
            userAnswer.setAppId(appId);
            userAnswer.setAppType(app.getAppType());
            userAnswer.setScoringStrategy(app.getScoringStrategy());
            userAnswer.setChoices(jsonStr);
            return userAnswer;
        }
        //定义锁
        RLock lock=redissonClient.getLock(AI_ANSWER_LOCK+cacheKey);
        try {
            boolean res=lock.tryLock(3,15,TimeUnit.SECONDS);

            if (res) {
                //1.根据id查询题目和题目结果信息
                Question question=questionService.getOne(
                        Wrappers.lambdaQuery(Question.class).eq(Question::getId,app.getId())
                );

                QuestionVO questionVO = QuestionVO.objToVo(question);
                List<QuestionContentDto> questionContent=questionVO.getQuestionContent();
                //调用ai获取结果
                String userMeagess=getAiTestScoringUserMessage(app,questionContent,choices);
                //ai生成
                String result=aiManager.doSyncStableRequest(AI_TEST_SCORING_SYSTEM_MESSAGE,userMeagess);
                // 结果处理
                int start = result.indexOf("{");
                int end = result.lastIndexOf("}");
                String json = result.substring(start, end + 1);
                //设置缓存
                answerCacheMap.put(cacheKey,json);

                //返回用户得分结果
                UserAnswer userAnswer=JSONUtil.toBean(json,UserAnswer.class);
                userAnswer.setAppId(appId);
                userAnswer.setAppType(app.getAppType());
                userAnswer.setScoringStrategy(app.getScoringStrategy());
                userAnswer.setChoices(JSONUtil.toJsonStr(choices));
                return userAnswer;
            }
        } finally {
            //todo 释放锁的逻辑
            if(lock!=null && lock.isLocked()){
                if(lock.isHeldByCurrentThread()){
                    lock.unlock();
                }
            }
        }
        return null;
    }
}
