package com.knowledge.server.app.qry;

import com.knowledge.core.dto.UserDto;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.domain.table.Table;
import com.knowledge.server.infrastructure.gateway.TableGateway;
import com.knowledge.server.web.response.PageResponse;
import com.knowledge.server.web.response.table.TableResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TableQry {
    @Autowired
    private TableGateway tableGateway;
    public Table selectObjById(Long id) {
        Table table = tableGateway.selectById(id);
        return table;
    }

    public PageResponse<TableResp> getPage(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        UserDto userDto = AuthUtils.getUserDto();
        queryParams.put("userId", userDto.getId());
        PageResponse<TableResp> response = tableGateway.selectPageResult(pageNum, pageSize, queryParams);
        return response;
    }
}
