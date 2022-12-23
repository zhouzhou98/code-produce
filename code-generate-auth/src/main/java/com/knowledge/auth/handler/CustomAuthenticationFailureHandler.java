package com.knowledge.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledge.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        // 响应错误信息：json格式
        response.setContentType("application/json;charset=UTF-8");
        String result = objectMapper.writeValueAsString(Result.failed(e.getMessage()));
        response.getWriter().write(result);
    }
}
