package com.knowledge.server.app.qry;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.GeneratorEnum;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.domain.generate.GenerateData;
import com.knowledge.server.domain.generate.generator.GeneratorFactory;
import com.knowledge.server.domain.generate.generator.LanguageGenerator;
import com.knowledge.server.domain.product.Project;
import com.knowledge.server.domain.table.Table;
import com.knowledge.server.infrastructure.gateway.DatasourceGateway;
import com.knowledge.server.infrastructure.gateway.ProjectGateway;
import com.knowledge.server.infrastructure.gateway.TableGateway;
import com.knowledge.server.web.request.table.GenerateDataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class GenerateQry {
    @Autowired
    private DatasourceGateway datasourceGateway;
    @Autowired
    private ProjectGateway projectGateway;
    @Autowired
    private TableGateway tableGateway;

    public String  download(Long datasourceId, List<Long> tableIds, Long projectId, String type) throws IOException {
        Datasource datasource = datasourceGateway.selectById(datasourceId);
        Project project = projectGateway.selectById(projectId);
        Predicate<Object> predicate = x -> x == null;
        UserDto userDto = AuthUtils.getUserDto();
        if (predicate.test(datasource)) {
            throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
        }
        if (predicate.test(project)) {
            throw new BizException(ResultCodeEnum.PROJECT_NOT_FOUND);
        }
        if (!userDto.getId().equals(datasource.getUser().getId()) || !userDto.getId().equals(project.getUser().getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        List<Table> tables = tableIds.stream().map(tableId -> {
            return tableGateway.selectById(tableId);
        }).filter(item -> {
            return !predicate.test(item);
        }).collect(Collectors.toList());
        GeneratorEnum typeEnum = Optional.ofNullable(GeneratorEnum.getEnumByValue(type))
                .orElse(GeneratorEnum.JAVA);
        LanguageGenerator generator = GeneratorFactory.getGenerator(tables, project, typeEnum);
        String url = generator.download();
        return url;
    }

    public Map<String,Object> generateDataJSON(Integer fakerNum, String tableName, List<GenerateDataField> fieldList) {
        GenerateData generateData = new GenerateData(tableName, fakerNum, fieldList);
        return generateData.toJSON();
    }
}
