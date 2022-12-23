package com.knowledge.server.infrastructure.gateway;

import com.knowledge.server.domain.product.Project;
import com.knowledge.server.entity.model.ProjectModel;
import com.knowledge.server.web.response.PageResponse;

import java.util.List;
import java.util.Map;

public interface ProjectGateway extends BaseGateway<Project, ProjectModel>{
    List<Project> selectListByUserId(Long userId);

    PageResponse<Map<String, Object>> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams);

    Project selectListByUserIdAndName(Long id, String projectName);
}
