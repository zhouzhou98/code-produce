package com.knowledge.user.infrastructure.gateway;

import com.baomidou.mybatisplus.extension.service.IService;
import com.knowledge.user.domain.user.User;
import com.knowledge.user.entity.model.UserModel;

public interface UserGateway extends IService<UserModel> {
    User selectByUsername(String username);

    int saveUser(User registerUser);

    User selectById(Long id);
}
