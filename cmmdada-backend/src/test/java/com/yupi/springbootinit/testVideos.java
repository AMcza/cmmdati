package com.yupi.springbootinit;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class testVideos {
    @Test
    public void test() {
        ClientV4 client = new ClientV4.Builder("5a20a04b90b780a225b967f56eff1687.8mOiLt7fOUUqoRYn").build();
        //调用API
        //构建请求
        List<ChatMessage> messages=new ArrayList<>();
        ChatMessage chatMessage=new ChatMessage(ChatMessageRole.USER.value(), "作为一名营销专家,请为智谱开放平台创作一个吸引人的slogan");
        messages.add(chatMessage);

        ChatCompletionRequest chatCompletionRequest=ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .maxTokens(100)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();
        ModelApiResponse invokeModelApiResp=client.invokeModelApi(chatCompletionRequest);
        System.out.println(invokeModelApiResp.toString());
    }
}
