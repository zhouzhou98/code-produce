package com.knowledge.server.web.request.project;

import javax.validation.constraints.NotNull;

public class ProjectAddOrUpdRequest {
    @NotNull(message = "项目名称不能为空", groups = ProjectAddOrUpdRequest.class)
    public String projectName;
    @NotNull(message = "项目编码不能为空", groups = ProjectAddOrUpdRequest.class)
    public String projectCode;
    @NotNull(message = "项目包名不能为空", groups = ProjectAddOrUpdRequest.class)
    public String projectPackage;
    @NotNull(message = "用户名不能为空", groups = ProjectAddOrUpdRequest.class)
    public String version;
    @NotNull(message = "作者不能为空", groups = ProjectAddOrUpdRequest.class)
    public String author;
    @NotNull(message = "邮箱不能为空", groups = ProjectAddOrUpdRequest.class)
    public String email;
}
