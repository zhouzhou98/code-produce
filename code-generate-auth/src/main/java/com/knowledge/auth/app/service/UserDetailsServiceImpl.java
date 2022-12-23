package com.knowledge.auth.app.service;

import com.knowledge.auth.app.qry.UserQry;
import com.knowledge.auth.domain.userInfo.UserInfoDetails;
import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserAuthDto;
import com.knowledge.core.enums.ResultCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserQry userQry;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 判断用户名是否为空
        if(StringUtils.isEmpty(username)) {
            throw new BizException("用户名不能为空");
        }
        UserAuthDto userAuthDto = userQry.getUserAuthDto(username);
        if (userAuthDto == null) {
            throw new BizException("用户不存在");
        }
        UserInfoDetails userInfoDetails = new UserInfoDetails(userAuthDto);
        return userInfoDetails;
    }
}
