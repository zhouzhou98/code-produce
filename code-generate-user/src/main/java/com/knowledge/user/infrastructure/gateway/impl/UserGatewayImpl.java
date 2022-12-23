package com.knowledge.user.infrastructure.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knowledge.user.app.mapper.UserModelMapper;
import com.knowledge.user.domain.user.User;
import com.knowledge.user.entity.model.UserModel;
import com.knowledge.user.infrastructure.converter.UserConverter;
import com.knowledge.user.infrastructure.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserGatewayImpl extends ServiceImpl<UserModelMapper, UserModel> implements UserGateway {
    @Autowired
    private UserConverter userConverter;
    @Override
    public User selectByUsername(String username) {
        LambdaQueryWrapper<UserModel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserModel::getUsername, username);
        UserModel userModel = this.baseMapper.selectOne(lambdaQueryWrapper);
        return userConverter.convertToDomainObject(userModel);
    }

    @Override
    public int saveUser(User registerUser) {
        UserModel userModel = this.userConverter.convertToDataModel(registerUser);
        return this.baseMapper.insert(userModel);
    }

    @Override
    public User selectById(Long id) {
        UserModel userModel = this.baseMapper.selectById(id);
        return userConverter.convertToDomainObject(userModel);
    }
}
