package com.knowledge.server.app.qry;

import com.knowledge.core.dto.UserDto;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.domain.product.Project;
import com.knowledge.server.infrastructure.gateway.ProjectGateway;
import com.knowledge.server.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProjectQry {
    @Autowired
    private ProjectGateway projectGateway;
    public List<Map<String, Object>> getList() {
        UserDto user = AuthUtils.getUserDto();
        Long userId = user.getId();
        List<Project> list = projectGateway.selectListByUserId(userId);
        return list.stream().map(item -> {
            return item.toJSON();
        }).collect(Collectors.toList());
    }

    public PageResponse<Map<String, Object>> getPage(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        UserDto userDto = AuthUtils.getUserDto();
        queryParams.put("userId", userDto.getId());
        PageResponse<Map<String, Object>> response = projectGateway.selectPageResult(pageNum, pageSize, queryParams);
        return response;
    }
}
