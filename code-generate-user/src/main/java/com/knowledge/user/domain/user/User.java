package com.knowledge.user.domain.user;

import com.knowledge.common.mybatis.model.BaseDomainObjEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseDomainObjEntity {
    /**
     * 用户名 电话号码
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
