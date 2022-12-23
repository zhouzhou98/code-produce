package com.knowledge.common.web.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.knowledge.core.constant.SecurityConstants;
import com.knowledge.core.enums.AuthenticationIdentityEnum;
import com.nimbusds.jose.JWSObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 请求工具类
 *
 * @author suyuzhou
 * @date 2022/07/15
 */
@Slf4j
public class RequestUtils {
    @SneakyThrows
    public static String getGrantType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String grantType = request.getParameter(SecurityConstants.GRANT_TYPE_KEY);
        return grantType;
    }

    /**
     * 获取登录认证的客户端ID
     * <p>
     * 兼容两种方式获取OAuth2客户端信息（client_id、client_secret）
     * 方式一：client_id、client_secret放在请求路径中
     * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
     *
     * @return
     */
    @SneakyThrows
    public static String getOAuth2ClientId() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 从请求路径中获取
        String clientId = request.getParameter(SecurityConstants.CLIENT_ID_KEY);
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }

        // 从请求头获取
        String basic = request.getHeader(SecurityConstants.AUTHORIZATION_KEY);
        if (StrUtil.isNotBlank(basic) && basic.startsWith(SecurityConstants.BASIC_PREFIX)) {
            basic = basic.replace(SecurityConstants.BASIC_PREFIX, Strings.EMPTY);
            String basicPlainText = new String(Base64.getDecoder().decode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            clientId = basicPlainText.split(":")[0]; //client:secret
        }
        return clientId;
    }

    /**
     * 解析JWT获取获取认证身份标识
     *
     * @return
     */
    @SneakyThrows
    public static String getAuthenticationIdentity() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String refreshToken = request.getParameter(SecurityConstants.REFRESH_TOKEN_KEY);

        String payload = StrUtil.toString(JWSObject.parse(refreshToken).getPayload());
        JSONObject jsonObject = JSONUtil.parseObj(payload);

        String authenticationIdentity = jsonObject.getStr(SecurityConstants.AUTHENTICATION_IDENTITY_KEY);
        if (StrUtil.isBlank(authenticationIdentity)) {
            authenticationIdentity = AuthenticationIdentityEnum.USERNAME.getValue();
        }
        return authenticationIdentity;
    }

    public static String[] extractAndDecodeHeader(String header) throws IOException {
        // `Basic ` 后面开始截取 clientId:clientSecret
        byte[] base64Token = header.trim().substring(6).getBytes(StandardCharsets.UTF_8);

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var8) {
            throw new RuntimeException("请求头解析失败：" + header);
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new RuntimeException("请求头无效：" + token);
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
