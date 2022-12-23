package com.knowledge.core.constant;

/**
 * 安全常量配置
 * @author suyuzhou
 * @date 2022/07/15
 */
public interface SecurityConstants {

    /**
     * 认证请求头key
     */
    String AUTHORIZATION_KEY = "Authorization";



    /**
     * Basic认证前缀
     */
    String BASIC_PREFIX = "Basic ";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";



    String USER_NAME_KEY = "username";

    String CLIENT_ID_KEY = "client_id";


    String GRANT_TYPE_KEY = "grant_type";

    String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * 认证身份标识
     */
    String AUTHENTICATION_IDENTITY_KEY = "authenticationIdentity";



}
