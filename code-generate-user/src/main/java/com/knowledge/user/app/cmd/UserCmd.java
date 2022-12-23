package com.knowledge.user.app.cmd;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.user.domain.user.User;
import com.knowledge.user.infrastructure.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserCmd {
    @Autowired
    private UserGateway userGateway;
    public int register(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String bcryPwd = encoder.encode(password);
        User user = userGateway.selectByUsername(username);
        if (user != null) {
            throw new BizException(ResultCodeEnum.USER_IS_EXIST);
        }
        User registerUser = User.builder()
                .username(username)
                .password(bcryPwd)
                .build();
        return userGateway.saveUser(registerUser);
    }
}
