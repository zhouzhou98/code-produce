package com.knowledge.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledge.common.web.utils.RequestUtils;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final String HEADER_TYPE = "Basic ";

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功 {}", authentication.getPrincipal());
        // 获取请求头中的客户端信息
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("header {}", header);

        // 响应结果对象
        Result result = null;

        try {

            if(header == null || !header.startsWith(HEADER_TYPE)) {
                throw new UnsupportedOperationException("请求头中无client信息");
            }
            // 解析请求头的客户端信息
            String[] tokens = RequestUtils.extractAndDecodeHeader(header);
            assert tokens.length == 2;

            String clientId = tokens[0];
            String clientSecret = tokens[1];

            // 查询客户端信息，核对是否有效
            ClientDetails clientDetails =
                    clientDetailsService.loadClientByClientId(clientId);
            if(clientDetails == null) {
                throw new UnsupportedOperationException("clientId对应的配置信息不存在：" + clientId);
            }
            // 校验客户端密码是否有效
            if( !passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
                throw new UnsupportedOperationException("无效clientSecret");
            }

            // 组合请求对象，去获取令牌
            TokenRequest tokenRequest =
                    new TokenRequest(MapUtils.EMPTY_MAP, clientId,
                            clientDetails.getScope(), "custom");

            OAuth2Request oAuth2Request =
                    tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication =
                    new OAuth2Authentication(oAuth2Request, authentication);

            // 获取 访问令牌对象
            OAuth2AccessToken accessToken =
                    authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

            result = Result.success(accessToken);
        } catch (Exception e) {
            log.error("认证成功处理器异常={}", e.getMessage(), e);
            result = Result.failed(ResultCodeEnum.AUTH_FAIL, e.getMessage());
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write( objectMapper.writeValueAsString( result ) );
    }
}
