package com.knowledge.server.app.cmd;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.domain.product.Project;
import com.knowledge.server.infrastructure.gateway.ProjectGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class ProjectCmd {
    @Autowired
    private ProjectGateway projectGateway;
    public int saveObject(String author, String email, String projectCode, String projectName, String projectPackage,String version) {
        UserDto userDto = AuthUtils.getUserDto();
        Project isExistProject = projectGateway.selectListByUserIdAndName(userDto.getId(), projectName);
        Predicate<Object> predicate = x -> x != null;
        if (predicate.test(isExistProject)) {
            throw new BizException(ResultCodeEnum.PROJECT_IS_EXIST);
        }
        Project project = Project.builder()
                .author(author)
                .email(email)
                .projectPackage(projectPackage)
                .user(userDto)
                .version(version)
                .projectCode(projectCode)
                .projectName(projectName)
                .build();
        return projectGateway.saveDomainObj(project);
    }

    public int updateObject(Long id, String author, String email, String projectCode, String projectName, String projectPackage, String version) {
        UserDto userDto = AuthUtils.getUserDto();
        Project project = projectGateway.selectById(id);
        if (!userDto.getId().equals(project.getUser().getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        Predicate<Object> predicate = x -> x != null;
        if (predicate.test(author)) {
            project.setAuthor(author);
        }
        if (predicate.test(email)) {
            project.setEmail(email);
        }
        if (predicate.test(projectCode)) {
            project.setProjectCode(projectCode);
        }
        if (predicate.test(projectName)) {
            project.setProjectName(projectName);
        }
        if (predicate.test(projectPackage)) {
            project.setProjectPackage(projectPackage);
        }
        if (predicate.test(version)) {
            project.setVersion(version);
        }
        return projectGateway.updateDomainObj(project);
    }

    public int delete(Long id) {
        Project project = projectGateway.selectById(id);
        Predicate<Object> predicate = x -> x == null;
        if (predicate.test(project)) {
            throw new BizException(ResultCodeEnum.PROJECT_NOT_FOUND);
        }
        UserDto userDto = AuthUtils.getUserDto();
        if (!userDto.getId().equals(project.getUser().getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        return projectGateway.deleteDomainObj(id);
    }
}
