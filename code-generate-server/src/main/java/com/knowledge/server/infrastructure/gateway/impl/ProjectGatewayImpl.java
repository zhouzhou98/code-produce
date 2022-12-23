package com.knowledge.server.infrastructure.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.app.mapper.ProjectModelMapper;
import com.knowledge.server.app.qry.UserQry;
import com.knowledge.server.domain.product.Project;
import com.knowledge.server.entity.model.ProjectModel;
import com.knowledge.server.infrastructure.gateway.ProjectGateway;
import com.knowledge.server.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ProjectGatewayImpl  extends BaseGatewayImpl<ProjectModel, ProjectModelMapper, Project> implements ProjectGateway {
    @Autowired
    private UserQry userQry;
    @Override
    public QueryWrapper<ProjectModel> doGetWrapper(Map<String, Object> queryParams) {
        Predicate<Object> predicate = x -> x != null;
        if (!predicate.test(queryParams.get("userId"))) {
            throw new BizException(ResultCodeEnum.PARAM_ERROR);
        }
        QueryWrapper<ProjectModel> wrapper = new QueryWrapper<>();
        wrapper.eq(predicate.test(queryParams.get("userId")) , "user_id", Long.valueOf(queryParams.get("userId").toString()));
        wrapper.like(predicate.test(queryParams.get("projectName")), "project_name", predicate.test(queryParams.get("projectName")) ? queryParams.get("projectName").toString() : null);
        wrapper.like(predicate.test(queryParams.get("projectCode")), "project_code", predicate.test(queryParams.get("project_code")) ? queryParams.get("projectCode").toString() : null);
        wrapper.like(predicate.test(queryParams.get("projectPackage")), "project_package", predicate.test(queryParams.get("projectPackage")) ? queryParams.get("projectPackage").toString() : null);
        wrapper.like(predicate.test(queryParams.get("version")), "version", predicate.test(queryParams.get("version")) ? queryParams.get("version").toString() : null);
        wrapper.like(predicate.test(queryParams.get("author")), "author", predicate.test(queryParams.get("author")) ? queryParams.get("author").toString() : null);
        wrapper.like(predicate.test(queryParams.get("email")), "author", predicate.test(queryParams.get("email")) ? queryParams.get("email").toString() : null);
        return wrapper;
    }

    @Override
    public PageResponse<Map<String, Object>> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        IPage<ProjectModel> page = this.getPage(pageNum, pageSize);
        Predicate<Project> predicate = x -> x != null;
        QueryWrapper<ProjectModel> wrapper = this.getWrapper(queryParams);
        IPage<ProjectModel> iPage = this.baseMapper.selectPage(page, wrapper);
        List<Map<String, Object>> list = iPage.getRecords().stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).map(item -> {
            return item.toJSON();
        }).collect(Collectors.toList());
        return new PageResponse<>(list, page.getTotal());
    }

    @Override
    public Project selectListByUserIdAndName(Long id, String projectName) {
        LambdaQueryWrapper<ProjectModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectModel::getUserId, id);
        wrapper.eq(ProjectModel::getProjectName, projectName);
        ProjectModel projectModel = this.baseMapper.selectOne(wrapper);
        return this.convertToDomainObject(projectModel);
    }

    @Override
    Project convertToDomainObject(ProjectModel dataModel) {
        Predicate<Object> predicate = x -> x == null;
        if (predicate.test(dataModel)) {
            return null;
        }
        UserDto userDto = userQry.getUserAuthDto(dataModel.getUserId());
        Project project = Project.builder()
                .author(dataModel.getAuthor())
                .projectCode(dataModel.getProjectCode())
                .projectName(dataModel.getProjectName())
                .projectPackage(dataModel.getProjectPackage())
                .user(userDto)
                .email(dataModel.getEmail())
                .version(dataModel.getVersion())
                .build();
        project.setId(dataModel.getId());
        project.setCreatedAt(dataModel.getCreatedAt());
        project.setUpdatedAt(dataModel.getUpdatedAt());
        project.setDataVersion(dataModel.getDataVersion());
        return project;
    }

    @Override
    ProjectModel convertToDataModel(Project domainObject) {
        Predicate<Object> predicate = x -> x == null;
        if (predicate.test(domainObject)) {
            return null;
        }
        ProjectModel model = ProjectModel.builder()
                .userId(domainObject.getUser().getId())
                .author(domainObject.getAuthor())
                .projectCode(domainObject.getProjectCode())
                .projectName(domainObject.getProjectName())
                .projectPackage(domainObject.getProjectPackage())
                .email(domainObject.getEmail())
                .version(domainObject.getVersion())
                .build();
        model.setCreatedAt(domainObject.getCreatedAt());
        model.setUpdatedAt(domainObject.getUpdatedAt());
        model.setId(domainObject.getId());
        model.setDataVersion(domainObject.getDataVersion());
        return model;
    }

    @Override
    public List<Project> selectListByUserId(Long userId) {
        LambdaQueryWrapper<ProjectModel> wrapper = new LambdaQueryWrapper<>();
        Predicate<Object> predicate = x -> x != null;
        wrapper.eq(ProjectModel::getUserId, userId);
        wrapper.orderByDesc(ProjectModel::getCreatedAt);
        return this.baseMapper.selectList(wrapper).stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).collect(Collectors.toList());
    }


}
