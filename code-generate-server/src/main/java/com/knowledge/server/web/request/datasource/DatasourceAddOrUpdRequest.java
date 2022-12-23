package com.knowledge.server.web.request.datasource;

import com.knowledge.server.web.request.validate.DatasourceAddValidateGroup;

import javax.validation.constraints.NotNull;

public class DatasourceAddOrUpdRequest {
    @NotNull(message = "数据库类型不能为空", groups = DatasourceAddValidateGroup.class)
    public String dbType;
    @NotNull(message = "连接名称不能为空", groups = DatasourceAddValidateGroup.class)
    public String conName;
    @NotNull(message = "连接url不能为空", groups = DatasourceAddValidateGroup.class)
    public String conUrl;
    @NotNull(message = "用户名不能为空", groups = DatasourceAddValidateGroup.class)
    public String username;
    @NotNull(message = "用户密码不能为空", groups = DatasourceAddValidateGroup.class)
    public String password;
}
