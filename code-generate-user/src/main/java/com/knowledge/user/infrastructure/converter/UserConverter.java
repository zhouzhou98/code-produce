package com.knowledge.user.infrastructure.converter;

import com.knowledge.core.converter.Converter;
import com.knowledge.user.domain.user.User;
import com.knowledge.user.entity.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserModel> {
    @Override
    public User convertToDomainObject(UserModel dataModel) {
        if (dataModel == null) {
            return null;
        }
        User user = User.builder()
                .password(dataModel.getPassword())
                .username(dataModel.getUsername())
                .build();
        user.setId(dataModel.getId());
        user.setCreatedAt(dataModel.getCreatedAt());
        user.setUpdatedAt(dataModel.getUpdatedAt());
        user.setDataVersion(dataModel.getDataVersion());
        return user;
    }

    @Override
    public UserModel convertToDataModel(User domainObject) {
        UserModel model = UserModel.builder()
                .password(domainObject.getPassword())
                .username(domainObject.getUsername())
                .build();
        model.setId(domainObject.getId());
        model.setCreatedAt(domainObject.getCreatedAt());
        model.setUpdatedAt(domainObject.getUpdatedAt());
        model.setDataVersion(domainObject.getDataVersion());
        return model;
    }
}
