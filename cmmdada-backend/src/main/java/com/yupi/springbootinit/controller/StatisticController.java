package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.*;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.mapper.UserAnswerMapper;
import com.yupi.springbootinit.model.dto.app.AppAddRequest;
import com.yupi.springbootinit.model.dto.app.AppEditRequest;
import com.yupi.springbootinit.model.dto.app.AppQueryRequest;
import com.yupi.springbootinit.model.dto.app.AppUpdateRequest;
import com.yupi.springbootinit.model.dto.statistic.AppAnswerCountDTO;
import com.yupi.springbootinit.model.dto.statistic.AppAnswerResultCountDTO;
import com.yupi.springbootinit.model.entity.App;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.enums.ReviewStatusEnum;
import com.yupi.springbootinit.model.vo.AppVO;
import com.yupi.springbootinit.service.AppService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 应用接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/app/statistic")
@Slf4j
public class StatisticController {
    @Resource
    private UserAnswerMapper userAnswerMapper;

    @GetMapping("/answer_count")
    public BaseResponse<List<AppAnswerCountDTO>> getAppAnswerCount(){
        return ResultUtils.success(userAnswerMapper.doAppAnswerCount());
    }

    @GetMapping("/answer_result_count")
    public BaseResponse<List<AppAnswerResultCountDTO>> getAppAnswerResultCount(Long appId){
        ThrowUtils.throwIf(appId==null || appId<=0,ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(userAnswerMapper.doAppAnswerResultCount(appId));
    }
}
