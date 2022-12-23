package com.knowledge.auth.web.controller;

import com.knowledge.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @GetMapping("/removeToken")
    public Result<String> removeToken(HttpServletRequest request) {
        // 获取访问令牌
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.replace("bearer ", "");
        if(StringUtils.isNotBlank(accessToken)) {
            OAuth2AccessToken oAuth2AccessToken =
                    tokenStore.readAccessToken(accessToken);
            if(oAuth2AccessToken != null) {
                // 删除redis中对应的访问令牌
                Object jti = oAuth2AccessToken.getAdditionalInformation().get("jti");
                tokenStore.removeAccessToken(oAuth2AccessToken);
                redisTemplate.delete(jti);
            }
        }
        return Result.success();
    }
}
