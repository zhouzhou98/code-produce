package com.knowledge.user.web.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRegisterRequest {
    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 12, message = "密码在6～12位")
    public String password;

    @NotNull(message = "用户名不能为空")
    public String username;
}
