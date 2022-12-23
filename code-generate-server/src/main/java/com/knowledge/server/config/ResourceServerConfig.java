package com.knowledge.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级别权限控制
@EnableResourceServer // 标识为资源服务器，请求资源接口时，必须在请求头带个access_token
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore); // jwt管理令牌
    }

    /**
     * 资源服务器的安全配置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement() // 采用token进行管理身份，而没有采用session，所以不需要创建HttpSession
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests() // 请求的授权配置
                // 放行以 注册 请求接口
                .antMatchers("/api/v1/user/register", "/api/v1/user/selectByUsername", "/api/v1/user/selectById", "/test").permitAll()
                // 所有请求都要有all范围权限
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                // 其他请求都要通过身份认证
                .anyRequest().authenticated();
    }
}
