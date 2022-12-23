package com.knowledge.user.app.qry;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserAuthDto;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.user.domain.user.User;
import com.knowledge.user.infrastructure.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserQry {
    @Autowired
    private UserGateway userGateway;
    /**
     * 根据用户名查找用户
     */
    public UserAuthDto selectByUsername(String username) {
        User user = userGateway.selectByUsername(username);
        if (user == null) {
            return null;
        }
        UserAuthDto authDto = UserAuthDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(Arrays.asList("admin"))
                .build();
        return authDto;
    }

    public UserDto selectById(Long id) {
        User user = userGateway.selectById(id);
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername());
    }
}
